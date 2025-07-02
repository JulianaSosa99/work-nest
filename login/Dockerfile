# 1) Etapa de build: compila tu .jar usando Maven
FROM maven:3.9.2-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
# Descarga dependencias sin compilar aún
RUN mvn dependency:go-offline -B

# Copia el código y genera el jar
COPY src ./src
RUN mvn package -DskipTests -B

# 2) Etapa de runtime: imagen ligera para ejecutar el jar
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Puedes definir valores por defecto (opcional)
ENV SPRING_PROFILES_ACTIVE=default \
    SERVER_PORT=3000

# Copia el artefacto generado en la etapa anterior
COPY --from=builder /app/target/login-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto (leerá SERVER_PORT)
EXPOSE ${SERVER_PORT}

# ENTRYPOINT que arranca tu aplicación
ENTRYPOINT ["sh","-c","java -jar app.jar"]
