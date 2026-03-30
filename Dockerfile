FROM maven:3.8-amazoncorretto-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/ ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring

USER spring

EXPOSE 9002

ENTRYPOINT ["java", "-jar", "app.jar"]