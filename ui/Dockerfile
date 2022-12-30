FROM openjdk:11-jdk as build
COPY target/ui-0.0.1-SNAPSHOT.jar docker-ui-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-ui-0.0.1-SNAPSHOT.jar"]