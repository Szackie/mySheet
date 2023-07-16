FROM adoptopenjdk/openjdk11:alpine-slim

RUN apk update && apk upgrade && apk add --no-cache bash curl

RUN mkdir /app

COPY . /app

WORKDIR /app

RUN chmod +x mvnw

RUN ./mvnw package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "/app/target/myForm-0.0.1-SNAPSHOT.jar"]