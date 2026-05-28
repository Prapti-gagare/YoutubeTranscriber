FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install packages
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl

# Install yt-dlp
RUN pip3 install --break-system-packages yt-dlp

# Copy requirements
COPY requirements.txt .

# Install Python libraries
RUN pip3 install --break-system-packages -r requirements.txt

# Copy project
COPY . .

# Backend folder
WORKDIR /app/backend

# Permission
RUN chmod +x mvnw

# Build jar
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Run jar directly
CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]