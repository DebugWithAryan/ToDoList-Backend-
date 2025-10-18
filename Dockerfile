
FROM maven:3.9.5-eclipse-temurin-21 As build

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw .
COPY mvnw.cmd .

RUN chmod +x = mvnw || true

COPY pom.xml .

RUN mvn dependency:go-offline -B || ./mvnw dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests -B || ./mvnw clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from = build /app/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS = "-Xmx512m -Xms256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT:-8080} -jar app.jar"]
