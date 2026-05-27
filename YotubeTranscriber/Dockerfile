FROM openjdk:17

RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl \
    && apt-get clean

WORKDIR /app

COPY . .

RUN pip3 install --no-cache-dir -r requirements.txt

RUN chmod +x backend/mvnw

WORKDIR /app/backend

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]