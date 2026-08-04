FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR /app
COPY pom.xml .
COPY atlas-shared atlas-shared
COPY atlas-infrastructure atlas-infrastructure
COPY atlas-microservices atlas-microservices
RUN ./mvnw package -DskipTests -pl atlas-infrastructure/atlas-config-service -am || true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/atlas-infrastructure/atlas-config-service/target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]
