FROM eclipse-temurin:22-jre-jammy
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -Pproduction
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/target/*.jar"]
