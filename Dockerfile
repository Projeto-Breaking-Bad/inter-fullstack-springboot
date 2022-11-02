FROM maven:3.6.3-jdk-8 as BUILD

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn spring-boot:build-image -DskipTests


FROM openjdk:8-jre-alpine

COPY --from=BUILD /build/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8080
