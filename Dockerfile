FROM ubuntu:latest
LABEL authors="Sharif"

RUN apt-get install -y \
    openjdk-22-jdk \
    maven

WORKDIR /app

COPY . .

RUN mvn -f pom.xml clean package

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["mvn", "spring-boot:run"]