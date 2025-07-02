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

# Ponemos por defecto el puerto 3000, pero se puede sobreescribir
ENV SERVER_PORT=3000

# Declara como argumentos de build (para que el linter no marque error)
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
ARG JWT_SECRET
ARG JWT_EXPIRATION
ARG SERVER_PORT=3000

# Pásalos a ENV para la aplicación en runtime
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV JWT_SECRET=${JWT_SECRET}
ENV JWT_EXPIRATION=${JWT_EXPIRATION}
ENV SERVER_PORT=${SERVER_PORT}

# Expone el puerto configurado
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar","/app/app.jar"]
