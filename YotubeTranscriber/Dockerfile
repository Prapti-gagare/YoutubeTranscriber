FROM openjdk:17

# Install Python + FFmpeg
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg

WORKDIR /app

# Copy full project
COPY . .

# Install Python dependencies
RUN pip3 install --no-cache-dir -r requirements.txt

# Go to backend
WORKDIR /app/backend

# Build Spring Boot project
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Run jar
CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]