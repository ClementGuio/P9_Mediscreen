FROM openjdk:11-jdk as build
LABEL org.opencontainers.image.authors="Clement Guio"
EXPOSE 8082
COPY target/docnote-api-0.0.1-SNAPSHOT.jar docker-docnote-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-docnote-api-0.0.1-SNAPSHOT.jar"]