import { getAccessToken, withApiAuthRequired } from "@auth0/nextjs-auth0";

export default withApiAuthRequired(async (req, res) => {
  const { accessToken } = await getAccessToken(req, res);

  const response = await fetch(`${process.env.BACKEND_URI}/unapproved`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${accessToken}`,
    },
  });

  const data = await response.json();

  res.status(response.status).json(data);
});
