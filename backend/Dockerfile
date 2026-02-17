ARG BACKEND_BUILD_IMAGE=maven:3.9-eclipse-temurin-17
FROM ${BACKEND_BUILD_IMAGE} AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

ARG BACKEND_RUNTIME_IMAGE=eclipse-temurin:17-jre
FROM ${BACKEND_RUNTIME_IMAGE}
WORKDIR /app
COPY --from=build /app/target/support-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
