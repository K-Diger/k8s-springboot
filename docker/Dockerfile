# Build stage
FROM openjdk:23-jdk AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean bootJar

# Run stage
FROM eclipse-temurin:23-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/build/libs/*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError"

ENTRYPOINT ["java", "-jar", "app.jar"]