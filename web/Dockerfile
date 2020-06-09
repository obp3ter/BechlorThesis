#FROM node:10
#WORKDIR /usr/src/app
#COPY package*.json ./
#
#RUN npm install
#COPY . .
#
#EXPOSE 4200
#CMD [ "npm","run", "start" ]

FROM node:10-alpine as build
WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:1.16.0-alpine as prod

COPY --from=build app/dist /usr/share/nginx/html

EXPOSE 4200

CMD ["nginx", "-g", "daemon off;"]
