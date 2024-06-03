# Etapa de construção
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de execução
FROM adoptopenjdk/openjdk11:x86_64-ubuntu-jdk-11.0.13_8-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Configurar parâmetros da JVM
ENV JAVA_OPTS="-Xmx500m -Xms500m -XX:+UseG1GC"

# Expor a porta
EXPOSE 8080

# Iniciar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --server.port=${PORT:-8080}"]
