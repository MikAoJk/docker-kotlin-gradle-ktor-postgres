FROM openjdk:18-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY build/libs/*-all.jar app.jar
CMD ["java", "-jar", "app.jar"]
