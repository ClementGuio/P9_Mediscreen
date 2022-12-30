FROM openjdk:11-jdk as build
LABEL org.opencontainers.image.authors="Clement Guio"
EXPOSE 8888
COPY target/patient-api-0.0.1-SNAPSHOT.jar docker-patient-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-patient-api-0.0.1-SNAPSHOT.jar"]