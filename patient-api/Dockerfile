FROM openjdk:11-jdk as build
MAINTAINER Clement Guio
COPY target/patient-api-0.0.1-SNAPSHOT.jar docker-patient-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-patient-api-0.0.1-SNAPSHOT.jar"]