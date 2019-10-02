FROM openjdk:8-jdk-alpine
MAINTAINER Fernando Natividade Luiz - fernando.luiz@pti.org.br

RUN apk update && apk add bash
RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY target/kafkaBroker-0.0.1-SNAPSHOT.jar producer.jar
ENTRYPOINT ["java","-jar","producer.jar"]
