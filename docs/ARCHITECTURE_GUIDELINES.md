# Atlas Architecture Guidelines

## Domain-Driven Design (DDD) & Clean Architecture
- **`atlas-core-domain`**: Pure business domain models, enums, interfaces (zero framework dependencies).
- **`atlas-common-dto`**: Data transfer objects shared across REST APIs.
- **`atlas-kafka-events`**: Event payloads published over Apache Kafka.
- **`atlas-microservices/`**: Independent, state-encapsulated Spring Boot services.
