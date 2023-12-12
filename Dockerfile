FROM amazoncorretto:17-alpine3.18-jdk as build

WORKDIR /app

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

RUN chmod +x ./gradlew

RUN ./gradlew build

RUN ls -a /app

FROM amazoncorretto:17-alpine3.18

WORKDIR /app

COPY --from=build /app/build/libs/BECC-Music-Service-1.0.0.jar ./BECC-Music-Service-1.0.0.jar

EXPOSE 8080

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "BECC-Music-Service-1.0.0.jar"]
