FROM node:16-alpine
WORKDIR /app

ENV NODE_ENV production
ENV PORT 3000

COPY public ./public
COPY .next/standalone ./
COPY /.next/static ./.next/static
RUN npm i sharp

EXPOSE 3000
ENV PORT 3000

ENTRYPOINT ["node", "server.js"]
