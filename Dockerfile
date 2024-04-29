FROM eclipse-temurin:17-jdk AS build
LABEL authors="apena"

VOLUME /tmp
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]