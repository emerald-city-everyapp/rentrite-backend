FROM eclipse-temurin:17-jdk-alpine as build

RUN addgroup -S -g 1000 builder
RUN adduser -D -S -G builder -u 1000 -s /bin/bash -h /home/builder builder

USER builder
WORKDIR /home/builder

COPY --chown=builder:builder . /home/builder
RUN ./gradlew build --warning-mode all --no-daemon

FROM eclipse-temurin:17-jdk-alpine

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/builder/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar","/app/app.jar"]
