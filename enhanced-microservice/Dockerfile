FROM openjdk:17
WORKDIR /
ADD target/enhanced-microservice-3.1.8.jar //
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/enhanced-microservice-3.1.8.jar"]