# Atlas Development Guide

## Environment Setup
- **Java**: 21 (OpenJDK / Temurin)
- **Build Tool**: Maven 3.9+
- **Database**: PostgreSQL 16
- **Cache**: Redis 7.2
- **Event Bus**: Apache Kafka 3.6
- **Node.js**: 18+ for React UI

## Local Development Commands

```bash
# Compile Java Reactor
mvn clean test-compile

# Execute All Unit Tests
mvn test

# Package Production Jars
mvn clean package -DskipTests

# Run React UI Dev Server
cd atlas-ui
npm run dev
```
