FROM node:latest

WORKDIR /app

COPY package.json .

COPY package-lock.json .

RUN npm install

# add app
COPY . .

EXPOSE 3000

CMD ["npm", "start"]
