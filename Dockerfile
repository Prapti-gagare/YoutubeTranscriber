FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install required packages
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl \
    git

# Install yt-dlp
RUN pip3 install --break-system-packages \
    "git+https://github.com/yt-dlp/yt-dlp.git"
# Copy requirements
COPY requirements.txt .

# Install Python packages
RUN pip3 install --break-system-packages -r requirements.txt

# Copy project
COPY . .

# Backend folder
WORKDIR /app/backend

# Maven permission
RUN chmod +x mvnw

# Build Spring Boot
RUN ./mvnw clean install -DskipTests

EXPOSE 8080

# Run app
CMD ["./mvnw", "spring-boot:run"]