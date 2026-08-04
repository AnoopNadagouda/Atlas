FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR /app
COPY pom.xml .
COPY atlas-shared atlas-shared
COPY atlas-infrastructure atlas-infrastructure
COPY atlas-microservices atlas-microservices
RUN ./mvnw package -DskipTests -pl atlas-microservices/atlas-index-builder-worker -am || true

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN apk add --no-cache curl
COPY --from=builder /app/atlas-microservices/atlas-index-builder-worker/target/*.jar app.jar
EXPOSE 8084
HEALTHCHECK --interval=15s --timeout=5s --retries=3 CMD curl -f http://localhost:8084/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
