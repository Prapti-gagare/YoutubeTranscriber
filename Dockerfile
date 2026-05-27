# JAVA
FROM eclipse-temurin:17-jdk

# INSTALL PYTHON
RUN apt-get update && \
    apt-get install -y python3 python3-pip ffmpeg

# WORKING DIRECTORY
WORKDIR /app

# COPY EVERYTHING
COPY . .

# INSTALL PYTHON LIBRARIES
RUN pip3 install --break-system-packages -r python/requirements.txt

# GO INSIDE BACKEND
WORKDIR /app/backend

# BUILD SPRING BOOT
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# EXPOSE PORT
EXPOSE 8080

# START APPLICATION
CMD ["java", "-jar", "target/transcriber-0.0.1-SNAPSHOT.jar"]