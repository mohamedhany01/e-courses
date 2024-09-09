FROM node:18.20.4-alpine3.20

EXPOSE 3000

RUN apk add --update tini

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install && npm cache clean --force

COPY . .

CMD ["/sbin/tini", "--", "node", "index.js"]