FROM openjdk:11-jdk as build
MAINTAINER Clement Guio
COPY target/riskreport-0.0.1-SNAPSHOT.jar docker-riskreport-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-riskreport-0.0.1-SNAPSHOT.jar"]