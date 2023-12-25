FROM openjdk:17-jdk-slim
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=build/libs/*.jar
ENTRYPOINT ["find", ".", "-name", "*.jar"]
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]