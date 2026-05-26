FROM eclipse-temurin:17-jdk-slim

# Install only required packages
RUN apt-get update && apt-get install -y --no-install-recommends \
    python3 \
    python3-pip \
    ffmpeg \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy backend first (better caching)
COPY backend/ ./backend

WORKDIR /app/backend

# Make mvnw executable
RUN chmod +x mvnw

# Install Python dependencies
RUN pip3 install --no-cache-dir --break-system-packages -r requirements.txt

# Build Spring Boot app
RUN ./mvnw clean package -DskipTests

# Run app
EXPOSE 8080

CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]