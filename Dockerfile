# Use an official OpenJDK base image
FROM openjdk:22-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY core/target/core-0.0.1-SNAPSHOT.jar  ergon.jar

# Expose the port your Spring Boot application will run on
EXPOSE 9993

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "ergon.jar"]