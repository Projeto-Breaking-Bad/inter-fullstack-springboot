FROM openjdk:11

WORKDIR /application

COPY target/*.jar /application/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
