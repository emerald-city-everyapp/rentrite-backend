FROM eclipse-temurin:17-jdk-alpine as build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --warning-mode --no-daemon all

FROM eclipse-temurin:17-jdk-alpine

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar","/app/app.jar"]
