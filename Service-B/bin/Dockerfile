FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./service-b.jar /app/service-b.jar

ENTRYPOINT ["java", "-jar", "service-b.jar"]
