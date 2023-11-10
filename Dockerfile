FROM openjdk:11 AS builder
WORKDIR /backend
ARG KEY_ID
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:11
COPY --from=builder /backend/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV KeyId=$KEY_ID

ENTRYPOINT ["java","-jar","/app.jar"]