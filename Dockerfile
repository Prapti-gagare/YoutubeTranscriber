FROM eclipse-temurin:17-jdk

WORKDIR /app

# Installing the system packages
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl \
    git \
    nodejs \
    npm \
    && rm -rf /var/lib/apt/lists/*



# Install yt-dlp directly from GitHub
#RUN pip3 install --break-system-packages --no-cache-dir \
    "git+https://github.com/yt-dlp/yt-dlp.git"

# Verify yt-dlp installed
RUN yt-dlp --version

# Copy requirements
COPY requirements.txt .

# Install Python packages
RUN pip3 install --break-system-packages -r requirements.txt

# Copy full project
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