import { useUser } from "@auth0/nextjs-auth0";
import { Button, Divider, TextField, Typography } from "@mui/material";
import { Box } from "@mui/system";
import Link from "next/link";
import { useState } from "react";
import Moment from "react-moment";
import PageWrapper from "../../../components/PageWrapper";
import ServerPing from "../../../components/ServerPing";

export const getServerSideProps = async (context) => {
  const { id } = context.params;

  const res = await fetch(`${process.env.BACKEND_URI}/unapproved/${id}`);
  const data = await res.json();

  if (res.status !== 200) {
    return {
      props: {
        error: data?.errorMessage,
      },
    };
  }

  return {
    props: {
      server: data,
    },
  };
};

const PendingServer = ({ server }) => {
  const { user, isLoading } = useUser();
  const [name, setName] = useState(server.name || "");
  const [notes, setNotes] = useState(server.notes || "");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);
  const [approved, setApproved] = useState(false);
  const [rejected, setRejected] = useState(false);

  const approve = async (approved) => {
    setLoading(true);
    setError(false);
    setApproved(false);
    setRejected(false);

    const res = await fetch(`/api/servers/approvals`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: server.id,
        approved,
        name,
        notes,
      }),
    });

    const data = await res.json();

    setLoading(false);

    if (res.status !== 200) {
      setError(data?.errorMessage);
      return;
    }

    setApproved(approved === true);
    setRejected(approved === false);
  };

  return (
    <PageWrapper title="Pending Servers">
      <Box sx={{ color: "#E3E3E3" }}>
        {Boolean(server) && (
          <>
            <Typography variant="h5" sx={{ marginTop: 3, marginBottom: 1 }}>
              {server.ip}
            </Typography>

            <Divider />

            <Box sx={{ marginTop: 3, marginBottom: 3 }}>
              <ServerPing
                key={server.ip}
                ip={server.ip}
                favicon={server.favicon}
                playerCount={server.playerCount}
                maxPlayers={server.maxPlayers}
                motd={server.motd}
                href={`/minecraft-servers/pending/${server.id}`}
              />
            </Box>

            <Divider />

            {server.approved === undefined && (
              <Typography variant="p" component="p" sx={{ marginTop: 3 }}>
                ⌚ Waiting for approval since{" "}
                <Moment date={server.createdAt} format="MM/DD/yyyy" /> at{" "}
                <Moment date={server.createdAt} format="hh:mma" />
              </Typography>
            )}

            {server.approved === false && (
              <Typography variant="p" component="p" sx={{ marginTop: 3 }}>
                ❌ Server was rejected. Reason: &quot;{server.notes}&quot;
              </Typography>
            )}

            {server.approved === true && (
              <Box sx={{ marginTop: 3 }}>
                <Typography variant="p" component="p">
                  ✅ Server was approved!
                </Typography>
                <Typography variant="p" component="p" sx={{ marginTop: 0.5 }}>
                  You can view the server details here:{" "}
                  <Link
                    href={`/minecraft-servers/${server.name}`}
                    style={{ color: "#90caf9" }}
                  >
                    {server.name}
                  </Link>
                </Typography>
              </Box>
            )}

            <Typography variant="p" component="p" sx={{ marginTop: 0.5 }}>
              <b>Version(s):</b> {server.versionName}
            </Typography>

            {server.approved === undefined && (
              <Typography
                variant="p"
                component="p"
                sx={{
                  marginTop: 5,
                  color: "lightgray",
                  maxWidth: 800,
                  fontSize: "0.9rem",
                }}
              >
                * Each server undergoes manual review to ensure it is legitimate
                and appropriate before being listed on our site. Servers with
                inappropriate themes are not permitted and will be rejected. We
                still track player counts and statistics of rejected servers,
                but they will not be present on our server lists and can only be
                viewed by accessing them directly via this page. Requests are
                typically processed within 48 hours, but in some cases may take
                longer.
              </Typography>
            )}

            {server.approved == false && (
              <Typography
                variant="p"
                component="p"
                sx={{
                  marginTop: 5,
                  color: "lightgray",
                  maxWidth: 800,
                  fontSize: "0.9rem",
                }}
              >
                * We still track player counts and statistics of rejected
                servers, but they will not be present on our server lists and
                can only be viewed by accessing them directly via this page.
              </Typography>
            )}

            {/* Admin approval section */}
            {!isLoading && user && (
              <>
                <Divider sx={{ marginTop: 4, marginBottom: 4 }} />
                <Typography
                  variant="h5"
                  component="h5"
                  sx={{ marginBottom: 2 }}
                >
                  Administrator Server Approval
                </Typography>
                <Box sx={{ maxWidth: 250 }}>
                  <TextField
                    label="Name"
                    sx={{ display: "block", marginBottom: 2 }}
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    fullWidth
                  />
                  <TextField
                    label="Notes"
                    sx={{ display: "block", marginBottom: 1 }}
                    value={notes}
                    onChange={(e) => setNotes(e.target.value)}
                    fullWidth
                  />
                  <Box sx={{ display: "flex", marginTop: 2 }}>
                    <Button
                      variant="contained"
                      color="error"
                      disabled={
                        loading || rejected || server.approved === false
                      }
                      onClick={() => approve(false)}
                    >
                      Reject
                    </Button>
                    <Button
                      variant="contained"
                      sx={{ marginLeft: 2 }}
                      disabled={loading || approved || server.approved === true}
                      onClick={() => approve(true)}
                    >
                      Approve
                    </Button>
                  </Box>
                </Box>

                {Boolean(error) && (
                  <Typography variant="p" component="p" sx={{ marginTop: 2 }}>
                    ⚠️ Oh no! An error occurred: &quot;{error}&quot;
                  </Typography>
                )}

                {Boolean(approved) && (
                  <Typography variant="p" component="p" sx={{ marginTop: 2 }}>
                    ✅ Successfully approved server.
                  </Typography>
                )}

                {Boolean(rejected) && (
                  <Typography variant="p" component="p" sx={{ marginTop: 2 }}>
                    ✅ Successfully rejected server.
                  </Typography>
                )}
              </>
            )}
          </>
        )}
      </Box>
    </PageWrapper>
  );
};

export default PendingServer;
