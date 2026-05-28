FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install system packages
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl

# Install yt-dlp
RUN pip3 install --break-system-packages yt-dlp

# Copy requirements
COPY requirements.txt .

# Install Python packages
RUN pip3 install --break-system-packages -r requirements.txt

# Copy full project
COPY . .

# Move to backend
WORKDIR /app/backend

# Give execute permission
RUN chmod +x mvnw

# Build project
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

# Run generated jar
CMD ["sh", "-c", "java -jar target/*.jar"]