FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
RUN ./gradlew build -x test
COPY ./src/main/resources/keystore.p12 keystore.p12
COPY ./build/libs/pz-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]