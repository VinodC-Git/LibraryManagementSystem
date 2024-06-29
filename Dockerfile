FROM eclipse-temurin:11-jdk

LABEL mentainer="netpractice123@gmail.com"

WORKDIR /app

COPY ./target/LibraryManagementSystem-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "LibraryManagementSystem-0.0.1-SNAPSHOT.jar"]

