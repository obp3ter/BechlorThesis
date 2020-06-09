FROM openjdk:11.0.7-jdk
ARG JAR_FILE=target/*.jar
ARG CONFIG=./src/main/resources/application.yaml
COPY ${JAR_FILE} app.jar
COPY ${CONFIG} application.yaml
EXPOSE 8883
ENTRYPOINT ["java","-jar","/app.jar","--spring.config.location=/application.yaml"]