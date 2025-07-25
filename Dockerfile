# Use a valid Maven + OpenJDK 17 image for build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (to leverage cache)
COPY pom.xml .

RUN mvn dependency:go-offline

# Copy the rest of your source code
COPY src ./src

# Package your application
RUN mvn clean package -DskipTests

# Second stage: minimal runtime image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the packaged jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on (adjust if needed)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
