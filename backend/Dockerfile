FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl

WORKDIR /app

COPY . .

# Install pip tools
RUN pip3 install --upgrade pip

# Install torch FIRST (stable CPU version)
RUN pip3 install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cpu

# Install remaining dependencies
RUN pip3 install -r requirements.txt

WORKDIR /app/backend

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]