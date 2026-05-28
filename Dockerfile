FROM eclipse-temurin:17-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    ffmpeg \
    curl \
    git \
    nodejs \
    npm

# Install latest yt-dlp
RUN pip3 install --break-system-packages \
    "git+https://github.com/yt-dlp/yt-dlp.git"

# Install JS runtime
RUN npm install -g nodejs

COPY requirements.txt .

RUN pip3 install --break-system-packages -r requirements.txt

COPY . .

WORKDIR /app/backend

RUN chmod +x mvnw

RUN ./mvnw clean install -DskipTests

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]