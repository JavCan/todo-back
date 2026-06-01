# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos el wrapper de maven y archivos de configuración
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Descargamos las dependencias (mejora el caché de Docker)
RUN ./mvnw dependency:go-offline

# Copiamos el código fuente y compilamos
COPY src src
RUN ./mvnw package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre

ARG APP_VERSION=dev
ENV APP_VERSION=${APP_VERSION}

WORKDIR /deployments

# Copiamos los artefactos construidos en el Stage 1
COPY --from=build /app/target/quarkus-app/lib/      lib/
COPY --from=build /app/target/quarkus-app/*.jar     ./
COPY --from=build /app/target/quarkus-app/app/      app/
COPY --from=build /app/target/quarkus-app/quarkus/  quarkus/

EXPOSE 8080

ENTRYPOINT ["java", \
  "-Dquarkus.http.host=0.0.0.0", \
  "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", \
  "-jar", "quarkus-run.jar"]
