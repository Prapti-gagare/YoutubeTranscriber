FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install dependencies
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl

# Install yt-dlp
RUN curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp \
    -o /usr/local/bin/yt-dlp

RUN chmod a+rx /usr/local/bin/yt-dlp

# Copy requirements
COPY requirements.txt .

# Install Python packages
RUN pip3 install --break-system-packages -r requirements.txt

# Copy full project
COPY . .

# Backend folder
WORKDIR /app/backend

# Maven wrapper permission
RUN chmod +x mvnw

# Build jar
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# RUN JAR DIRECTLY
CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]