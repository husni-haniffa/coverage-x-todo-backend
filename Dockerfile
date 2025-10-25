FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY target/coverage-x-todo-backend-0.0.1-SNAPSHOT.jar app.jar

COPY env.properties env.properties

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.import=file:env.properties"]
