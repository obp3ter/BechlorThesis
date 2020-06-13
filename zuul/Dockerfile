FROM openjdk:11.0.7-jre
ARG JAR_FILE=target/*.jar
ARG CONFIG=./src/main/resources/application.yaml
COPY ${JAR_FILE} app.jar
COPY ${CONFIG} application.yaml
