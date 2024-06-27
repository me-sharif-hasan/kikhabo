# Stage 1: Build the JAR file
FROM jelastic/maven:3.9.5-openjdk-21 as build
ENV GEMINI_KEY=$GEMINI_KEY
# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the project and package it as a JAR
RUN mvn clean package

# Stage 2: Run the JAR file
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/kikhabo.jar /app/kikhabo.jar
EXPOSE 8080
# Specify the command to run the JAR file
CMD ["java", "-jar", "kikhabo.jar"]
