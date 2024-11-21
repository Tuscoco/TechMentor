# Estágio de build
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
RUN apt-get install maven -y

# Copie o projeto para o contêiner
COPY . /Codigo

WORKDIR /Codigo
RUN mvn clean install && ls -l /Codigo/target  # Verifique se o JAR foi gerado corretamente

# Estágio final
FROM openjdk:21-jdk-slim

EXPOSE 4567

# Copiar o JAR gerado para o estágio final
COPY --from=build /Codigo/target/formulario-banco-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
