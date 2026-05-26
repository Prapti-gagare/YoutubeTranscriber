FROM openjdk:17

RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg

WORKDIR /app

COPY . .

RUN pip3 install -r requirements.txt

WORKDIR /app/backend

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]