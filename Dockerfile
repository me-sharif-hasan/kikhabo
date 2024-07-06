FROM ubuntu:latest
RUN export $(cat .env | xargs)
RUN apt-get update && \
    apt-get install -y \
    openjdk-21-jdk \
    maven

WORKDIR /app

COPY . .

RUN mvn -f pom.xml clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["mvn", "spring-boot:run"]