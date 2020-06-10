FROM node:10
WORKDIR /usr/src/app
COPY package*.json ./

RUN npm install
COPY . .

RUN touch ./bin/config.json

EXPOSE 3000
#CMD [ "./wait-for-it.sh", "localhost:8880", "--","npm", "run", "start" ]