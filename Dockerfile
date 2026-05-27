FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install Python
RUN apt-get update && apt-get install -y python3 python3-pip ffmpeg

# Copy requirements
COPY requirements.txt .

# Install Python packages
RUN pip3 install --break-system-packages -r requirements.txt

# Copy full project
COPY . .

# Build Spring Boot project
WORKDIR /app/backend

RUN chmod +x mvnw

RUN ./mvnw clean install -DskipTests

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
