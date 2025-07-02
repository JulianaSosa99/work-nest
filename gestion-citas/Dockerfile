###############
#   Builder   #
###############
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -B -DskipTests -Dmaven.resources.skip=true

###############
#   Runtime   #
###############
FROM eclipse-temurin:17-jre
WORKDIR /app

ARG JAR=gestion-citas-0.0.1-SNAPSHOT.jar
COPY --from=builder /app/target/${JAR} app.jar

# No hace falta redeclarar ARGs para las env vars de Render.
# Simplemente exponemos un puerto por claridad:
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
