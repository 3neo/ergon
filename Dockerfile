# Use an official OpenJDK base image
FROM openjdk:22-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/ergon-app.jar ergon.jar

# Expose the port your Spring Boot application will run on
EXPOSE 9993

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
