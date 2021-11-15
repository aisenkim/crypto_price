#FROM adoptopenjdk/openjdk11:latest
#ARG JAR_FILE=target/cryptoprice-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM adoptopenjdk/openjdk11:latest
CMD ["./mvnw", "clean", "package"]
#ARG JAR_FILE_PATH=target/*.jar
ARG JAR_FILE=target/cryptoprice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]