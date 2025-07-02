###############
# 1) Builder  #
###############
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# Copia el pom y el código
COPY pom.xml .
COPY src ./src

# Evita el filtering de recursos (se saltará la fase resources)
RUN mvn package -B -DskipTests -Dmaven.resources.skip=true

###############
# 2) Runtime  #
###############
FROM eclipse-temurin:17-jre
WORKDIR /app

#
# Copiamos el JAR generado
#
ARG JAR=login-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/target/${JAR} app.jar

# No hace falta redeclarar ARGs para las env vars de Render.
# Simplemente exponemos un puerto por claridad:
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
