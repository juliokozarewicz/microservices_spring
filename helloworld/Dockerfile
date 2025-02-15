FROM openjdk:17-jdk-slim as build
WORKDIR /app
COPY build/libs/helloworld.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]