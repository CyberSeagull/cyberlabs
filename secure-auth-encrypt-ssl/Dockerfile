FROM node:14 as build-stage

WORKDIR /src/app

COPY package*.json ./
COPY yarn.lock ./

RUN yarn install --silent

COPY . .
RUN yarn run build


EXPOSE 3000
ENTRYPOINT ["npm", "run", "start"]
