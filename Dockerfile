FROM eclipse-temurin:21.0.2_13-jdk-jammy AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN --mount=type=cache,id=s/25859712-993c-4262-b321-dffbb28df743,target=/root/.m2 ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -Pproduction -DskipTests

FROM eclipse-temurin:21.0.2_13-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
