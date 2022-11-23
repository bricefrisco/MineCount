import { getAccessToken, withApiAuthRequired } from "@auth0/nextjs-auth0";

export default withApiAuthRequired(async (req, res) => {
  if (req.method !== "PUT") {
    res.status(405).json({ errorMessage: "Method not allowed" });
    return;
  }

  const { accessToken } = await getAccessToken(req, res);

  const response = await fetch(`${process.env.BACKEND_URI}/servers/approvals`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${accessToken}`,
    },
    body: JSON.stringify(req.body),
  });

  const data = await response.json();

  res.status(response.status).json(data);
});
