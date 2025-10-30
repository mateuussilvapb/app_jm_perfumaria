# # --- STAGE 1: BUILD THE APPLICATION ---
# # Use a JDK image to build the project. We are now using Amazon Corretto 21 JDK.
# FROM amazoncorretto:21-jdk AS builder

# # Set the working directory inside the container
# WORKDIR /app

# # Copy the Maven project files (pom.xml) first to leverage Docker layer caching
# COPY pom.xml .

# # Copy the source code
# COPY src /app/src

# # Package the application (skipping tests for a faster build)
# # The resulting JAR is typically named target/my-app-0.0.1-SNAPSHOT.jar
# RUN ./mvnw clean package -DskipTests

# --- STAGE 2: CREATE THE FINAL, LIGHTWEIGHT IMAGE ---
# Use a smaller JRE image for the final runtime environment, specifically Corretto 21 JRE Alpine
# FROM amazoncorretto:21-alpine-jdk

# # Set the working directory
# # WORKDIR /app

# # Expose the port your Spring Boot app runs on (default is 8080)
# EXPOSE 8081

# # Copy the built JAR file from the 'builder' stage
# # We are copying the single JAR file created in the target directory to 'app.jar'
# COPY /target/*.jar /app.jar

# # Define the entry point for the container to run the JAR
# ENTRYPOINT ["java", "-jar", "/app/app.jar"]

FROM amazoncorretto:21-alpine-jdk

# Set working dir inside container
WORKDIR /app

# Expose the port Spring Boot runs on
EXPOSE 8081

# Copy the built jar into the container
COPY ./target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
