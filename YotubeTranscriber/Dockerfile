# ===============================
# Base Image (Java + OS)
# ===============================
FROM openjdk:17

# ===============================
# Install Python + FFmpeg + pip
# ===============================
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl \
    && apt-get clean

# ===============================
# Set working directory
# ===============================
WORKDIR /app

# ===============================
# Copy full project into container
# ===============================
COPY . .

# ===============================
# Install Python dependencies
# ===============================
RUN pip3 install --no-cache-dir -r requirements.txt

# ===============================
# Build Spring Boot backend
# ===============================
WORKDIR /app/backend

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

# ===============================
# Expose port
# ===============================
EXPOSE 8080

# ===============================
# Run Spring Boot application
# ===============================
CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]