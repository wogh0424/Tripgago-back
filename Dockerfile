FROM openjdk:11 AS builder
WORKDIR /backend
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:11
COPY --from=builder /backend/build/libs/*.jar app.jar
ARG KEY_ID
ENV SPRING_PROFILES_ACTIVE=prod
ENV KeyId=$KEY_ID

ENTRYPOINT ["java","-jar","/app.jar"]