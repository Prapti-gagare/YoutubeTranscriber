FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install Python + ffmpeg + curl
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl

# Install latest yt-dlp
RUN pip3 install --break-system-packages --upgrade pip

RUN pip3 install --break-system-packages \
    "git+https://github.com/yt-dlp/yt-dlp.git"

# Copy requirements file
COPY requirements.txt .

COPY cookies.txt .

# Install Python dependencies
RUN pip3 install --break-system-packages -r requirements.txt

# Copy complete project
COPY . .

# Move to backend
WORKDIR /app/backend

# Give permission to mvnw
RUN chmod +x mvnw

# Build project
RUN ./mvnw clean install -DskipTests

EXPOSE 8080

# Start Spring Boot app
CMD ["./mvnw", "spring-boot:run"]