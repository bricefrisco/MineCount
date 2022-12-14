package com.minecount.services;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.minecount.models.MCPingOptions;
import com.minecount.models.MCPingResponse;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.minidns.hla.ResolverApi;
import org.minidns.hla.SrvResolverResult;
import org.minidns.record.SRV;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MCPing {

    private static final Gson GSON = new Gson();
    private static final String SRV_QUERY_PREFIX = "_minecraft._tcp.%s";

    public static MCPingResponse getPing(final String address) throws IOException {
        return getPing(new MCPingOptions(address));
    }

    public static MCPingResponse getPing(final MCPingOptions options) throws IOException {
        Preconditions.checkNotNull(options.getHostname(), "Hostname cannot be null.");

        String hostname = options.getHostname();
        int port = options.getPort();

        try {
            SrvResolverResult result = ResolverApi.INSTANCE.resolveSrv(String.format(SRV_QUERY_PREFIX, hostname));
            if (result.wasSuccessful()) {
                for (SRV record : result.getAnswers()) {
                    hostname = record.target.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json;
        long ping = -1;

        try (final Socket socket = new Socket()) {
            long start = System.currentTimeMillis();
            socket.connect(new InetSocketAddress(hostname, port), options.getTimeout());
            ping = System.currentTimeMillis() - start;

            try (DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 //> Handshake
                 ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
                 DataOutputStream handshake = new DataOutputStream(handshake_bytes)) {

                handshake.writeByte(MCPingUtil.PACKET_HANDSHAKE);
                MCPingUtil.writeVarInt(handshake, options.getProtocolVersion());
                MCPingUtil.writeVarInt(handshake, options.getHostname().length());
                handshake.writeBytes(options.getHostname());
                handshake.writeShort(options.getPort());
                MCPingUtil.writeVarInt(handshake, MCPingUtil.STATUS_HANDSHAKE);

                MCPingUtil.writeVarInt(out, handshake_bytes.size());
                out.write(handshake_bytes.toByteArray());

                //> Status request
                out.writeByte(0x01); // Size of packet
                out.writeByte(MCPingUtil.PACKET_STATUSREQUEST);

                //< Status response
                MCPingUtil.readVarInt(in); // Size
                int id = MCPingUtil.readVarInt(in);

                MCPingUtil.io(id == -1, "Server prematurely ended stream.");
                MCPingUtil.io(id != MCPingUtil.PACKET_STATUSREQUEST, "Server returned invalid packet.");

                int length = MCPingUtil.readVarInt(in);
                MCPingUtil.io(length == -1, "Server prematurely ended stream.");
                MCPingUtil.io(length == 0, "Server returned unexpected value.");

                byte[] data = new byte[length];
                in.readFully(data);
                json = new String(data, options.getCharset());

                //> Ping
                out.writeByte(0x09); // Size of packet
                out.writeByte(MCPingUtil.PACKET_PING);
                out.writeLong(System.currentTimeMillis());

                //< Ping
                MCPingUtil.readVarInt(in); // Size
                id = MCPingUtil.readVarInt(in);
                MCPingUtil.io(id == -1, "Server prematurely ended stream.");
                MCPingUtil.io(id != MCPingUtil.PACKET_PING, "Server returned invalid packet.");

            }

        }

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonElement descriptionJsonElement = jsonObject.get("description");

        if (descriptionJsonElement.isJsonObject()) {

            // For those versions that work with TextComponent MOTDs

            JsonObject descriptionJsonObject = jsonObject.get("description").getAsJsonObject();

            if (descriptionJsonObject.has("extra")) {
                descriptionJsonObject.addProperty("text", new TextComponent(ComponentSerializer.parse(descriptionJsonObject.get("extra").getAsJsonArray().toString())).toLegacyText());
                jsonObject.add("description", descriptionJsonObject);
            }

        } else {

            // For those versions that work with String MOTDs

            String description = descriptionJsonElement.getAsString();
            JsonObject descriptionJsonObject = new JsonObject();
            descriptionJsonObject.addProperty("text", description);
            jsonObject.add("description", descriptionJsonObject);

        }

        MCPingResponse output = GSON.fromJson(jsonObject, MCPingResponse.class);
        output.setPing(ping);
        output.setHostname(hostname);
        output.setPort(port);

        return output;
    }

}
