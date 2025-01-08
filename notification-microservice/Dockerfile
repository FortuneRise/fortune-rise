# Step 1: Use Eclipse Temurin as the base image for Java 17
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the built .jar file into the container
COPY notification.api/target/notification.api-1.0.0-SNAPSHOT.jar /app/notification.api-1.0.0-SNAPSHOT.jar

# Step 4: Expose the port the microservice will run on
EXPOSE 8083

# Step 5: Define the command to run the microservice
CMD ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "-jar", "notification.api-1.0.0-SNAPSHOT.jar"]
