# Stage 1: Build the application
FROM gradle:7.3.3-jdk17 as builder

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and settings files
COPY gradlew .
COPY gradle /app/gradle

# Copy the project files
COPY build.gradle.kts settings.gradle.kts ./
COPY src /app/src

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the project
RUN ./gradlew build

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar from the build stage and rename it to techfood-clientes-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/build/libs/*.jar techfood-clientes-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "techfood-clientes-0.0.1-SNAPSHOT.jar"]
