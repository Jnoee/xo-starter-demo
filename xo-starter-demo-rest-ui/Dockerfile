FROM node:8.0.0

COPY dist/ /app/
WORKDIR /app

RUN npm config set registry https://registry.npm.taobao.org 
RUN npm install -g http-server

EXPOSE 8080

CMD ["http-server"]