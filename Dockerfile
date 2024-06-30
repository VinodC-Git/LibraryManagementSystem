FROM openjdk:17-jdk-alpine

LABEL mentainer="test123@gmail.com"

WORKDIR /app

COPY ./target/LibraryManagementSystem-0.0.1-SNAPSHOT.jar /app

EXPOSE 8088

CMD ["java", "-jar", "LibraryManagementSystem-0.0.1-SNAPSHOT.jar"]

