FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install Python and FFmpeg
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl

# Install yt-dlp
RUN curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -o /usr/local/bin/yt-dlp

RUN chmod a+rx /usr/local/bin/yt-dlp

# Copy requirements file
COPY requirements.txt .

# Install Python packages
RUN pip3 install --break-system-packages -r requirements.txt

# Copy all project files
COPY . .

# Create folders
RUN mkdir -p downloads
RUN mkdir -p audio
RUN mkdir -p transcripts

# Go to backend folder
WORKDIR /app/backend

# Give permission
RUN chmod +x mvnw

# Build project
RUN ./mvnw clean install -DskipTests

EXPOSE 8080

# Start project
CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]