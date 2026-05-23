import whisper
import sys
import os
import time

# GET AUDIO PATH FROM JAVA
audio_path = sys.argv[1]

print("Audio File:", audio_path)

# LOAD BETTER MULTILINGUAL MODEL
model = whisper.load_model("medium")

print("Whisper model loaded successfully")

# AUTOMATIC LANGUAGE DETECTION
result = model.transcribe(
    audio_path,
    fp16=False
)

# DETECTED LANGUAGE
detected_language = result["language"]

print("Detected Language:", detected_language)

# EXTRACT TRANSCRIPT
transcript = result["text"]

# CREATE TRANSCRIPTS FOLDER
os.makedirs("../transcripts", exist_ok=True)

# UNIQUE TRANSCRIPT FILE
timestamp = int(time.time())

transcript_path = f"../transcripts/transcript_{timestamp}.txt"

# SAVE TRANSCRIPT
with open(transcript_path, "w", encoding="utf-8") as file:
    file.write(transcript)

print("\nTRANSCRIPT:\n")
print(transcript)

print("\nTRANSCRIPT_PATH=" + transcript_path)