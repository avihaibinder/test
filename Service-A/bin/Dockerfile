FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./service-a.jar /app/service-a.jar

ENTRYPOINT ["java", "-jar", "service-a.jar"]
