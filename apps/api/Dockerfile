FROM node:22-alpine

ARG DATABASE_URL

ENV DATABASE_URL=$DATABASE_URL

WORKDIR /app

COPY package*.json ./
COPY tsconfig*.json ./
COPY prisma.config.ts ./
COPY prisma ./prisma

RUN npm install

COPY . .

COPY entrypoint.sh .
RUN chmod +x entrypoint.sh

EXPOSE 3000

ENTRYPOINT ["./entrypoint.sh"]
