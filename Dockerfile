FROM adoptopenjdk/openjdk11:alpine-slim

RUN apk update && apk upgrade && apk add --no-cache bash curl

RUN mkdir /app

COPY . /app

WORKDIR /app

RUN chmod +x mvnw

RUN ./mvnw install -DskipTests

ENTRYPOINT ["java", "-jar", "/app/target/myForm-0.0.1-SNAPSHOT.jar"]