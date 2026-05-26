package com.youtube.transcriber.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class AudioService {

    public String extractAudio(String videoPath) {

        try {

            // UNIQUE AUDIO NAME
            long timestamp = System.currentTimeMillis();

           String audioPath = "../audio/audio_" + timestamp + ".wav";
           
            ProcessBuilder processBuilder = new ProcessBuilder(
        "ffmpeg",
        "-y",
        "-i",
        videoPath,

        // REMOVE VIDEO
        "-vn",

        // MONO AUDIO
        "-ac",
        "1",

        // 16k SAMPLE RATE
        "-ar",
        "16000",

        // WAV FORMAT
        "-acodec",
        "pcm_s16le",

        audioPath
);

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                return null;
            }

            File audioFile = new File(audioPath);

            if (audioFile.exists()) {
                return audioFile.getAbsolutePath();
            }

            return null;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}