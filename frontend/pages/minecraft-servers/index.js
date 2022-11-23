import { Button, TextField, Tooltip } from "@mui/material";
import { Box } from "@mui/system";
import PageWrapper from "../../components/PageWrapper";
import TopPagination from "../../components/TopPagination";

import Server from "../../components/Server";
import Link from "next/link";
import BottomPagination from "../../components/BottomPagination";

export const getServerSideProps = async (context) => {
  const page = Number(context.query?.page) || 1;

  const res = await fetch(
    `${process.env.BACKEND_URI}/servers?page=${page - 1}`
  );

  const data = await res.json();

  return {
    props: {
      servers: data?.servers || null,
      page,
      count: data?.count || null,
      pageCount: data?.pageCount || null,
    },
  };
};

const MinecraftServers = ({ servers, page, count, pageCount }) => {
  return (
    <PageWrapper title="Minecraft Servers">
      <Box sx={{ maxWidth: 1000 }}>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <TopPagination
            href="/minecraft-servers"
            page={page}
            count={count}
            pageCount={pageCount}
          />
          <Box sx={{ display: "flex" }}>
            <Tooltip title="Search for a specific server" placement="top">
              <TextField
                variant="outlined"
                sx={{ marginRight: 2, "& input": { padding: 1 } }}
                placeholder="Search..."
              />
            </Tooltip>
            <Link
              href="/minecraft-servers/add"
              style={{
                textDecoration: "none!important",
                "&:visited": { textDecoration: "none!important" },
              }}
            >
              <Tooltip title="Add a new server" placement="top">
                <Button
                  variant="outlined"
                  sx={{
                    textDecoration: "none!important",
                    "&:visited": { textDecoration: "none!important" },
                    height: "100%",
                  }}
                >
                  Add Server
                </Button>
              </Tooltip>
            </Link>
          </Box>
        </Box>

        {servers.map((server) => (
          <Server server={server} key={server.ip} />
        ))}

        <Box sx={{ display: "flex", justifyContent: "center" }}>
          <BottomPagination
            href="/minecraft-servers"
            page={page}
            pageCount={pageCount}
          />
        </Box>
      </Box>
    </PageWrapper>
  );
};

export default MinecraftServers;
