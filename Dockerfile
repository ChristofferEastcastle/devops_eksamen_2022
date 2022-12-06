FROM maven:3-amazoncorretto-11 as builder
COPY . .
RUN mvn package -DskipTests

FROM amazoncorretto:11-alpine-jdk
COPY --from=builder /target/onlinestore-0.0.1-SNAPSHOT.jar /app/application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]