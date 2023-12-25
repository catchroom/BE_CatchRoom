FROM openjdk:17-jdk-slim
CMD ["./mvnw", "clean", "package"]
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]