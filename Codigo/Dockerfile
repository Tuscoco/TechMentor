# Estágio de build
FROM ubuntu:latest AS build

# Instala dependências necessárias
RUN apt-get update && apt-get install -y openjdk-21-jdk maven

# Define o diretório de trabalho
WORKDIR /Codigo

# Copia a pasta do código para o contêiner
COPY Codigo /Codigo

# Gera o build do Maven
RUN mvn clean install

# Estágio de execução
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta do serviço
EXPOSE 4567

# Copia o JAR do estágio de build para o contêiner final
COPY --from=build /Codigo/target/formulario-banco-1.0-SNAPSHOT.jar app.jar

# Copia os arquivos estáticos para o contêiner
COPY --from=build /Codigo/src/main/resources/public /app/public

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
