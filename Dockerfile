# Stage 1: Build the JAR file
FROM maven:3.9.5-sapmachine-21 as build
RUN --mount=type=secret,id=_env,dst=/etc/secrets/.env cat /etc/secrets/.env

ENV GEMINI_KEY=$GEMINI_KEY
ENV JWT_SECRET=$JWT_SECRET
ENV AIVEN_CLOUD_KEY=$AIVEN_CLOUD_KEY

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the project and run tests
RUN mvn clean package

# Stage 2: Run the JAR file
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/kikhabo.jar /app/kikhabo.jar

# Expose port 8080
EXPOSE 8080

# Specify the command to run the JAR file
CMD ["java", "-jar", "kikhabo.jar"]
