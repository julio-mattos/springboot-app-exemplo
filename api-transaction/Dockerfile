FROM amazoncorretto:11

WORKDIR /app

COPY target/api-transaction*.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]





