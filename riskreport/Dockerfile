FROM openjdk:11-jdk as build
LABEL org.opencontainers.image.authors="Clement Guio"
EXPOSE 8084
COPY target/riskreport-0.0.1-SNAPSHOT.jar docker-riskreport-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-riskreport-0.0.1-SNAPSHOT.jar"]