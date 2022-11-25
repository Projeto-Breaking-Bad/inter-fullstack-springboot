FROM arm64v8/maven:3.8-amazoncorretto-11 as BUILD

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn spring-boot:build-image -DskipTests


FROM openjdk:11-jre-slim

COPY --from=BUILD /build/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080
