FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y

WORKDIR /app

COPY . .

RUN apt-get install maven -y

WORKDIR /app/Codigo
RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 4567

WORKDIR /app

COPY --from=build Codigo/target/formulario-banco-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]