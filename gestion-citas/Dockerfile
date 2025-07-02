###############
#   Builder   #
###############
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# 1) Copia POM y código fuente
COPY pom.xml .
COPY src ./src

# 2) Empaqueta sin tests y sin incluir recursos filtrados
RUN mvn package -B -DskipTests -Dmaven.resources.skip=true

###############
#   Runtime   #
###############
FROM eclipse-temurin:17-jre
WORKDIR /app

# 3) Copia el JAR empaquetado (cualquier nombre) a app.jar
COPY --from=builder /app/target/*.jar app.jar

# 4) Documenta el puerto interno (Spring leerá ${PORT})
EXPOSE 8080

# 5) Lanza la aplicación
ENTRYPOINT ["java","-jar","/app/app.jar"]
