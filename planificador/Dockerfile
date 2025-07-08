# 1) Build stage: compila con Maven
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# Copia pom y código fuente
COPY pom.xml .
COPY src ./src

# Empaqueta sin tests
RUN mvn clean package -B -DskipTests

# 2) Runtime stage: imagen ligera con JRE
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el JAR generado
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto (Render inyecta $PORT)
EXPOSE 10000

# Arranque usando la variable $PORT que Render provee
ENTRYPOINT ["sh","-c","java -Dserver.port=$PORT -jar /app/app.jar"]