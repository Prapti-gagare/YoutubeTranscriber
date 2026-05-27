FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install Python + ffmpeg
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

# Install Python libraries
RUN pip3 install --break-system-packages -r requirements.txt

# Copy project
COPY . .

# Move to backend
WORKDIR /app/backend

# Maven wrapper permission
RUN chmod +x mvnw

# Build project
RUN ./mvnw clean install -DskipTests

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]