package com.youtube.transcriber.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class AudioService {

    public boolean extractAudio() {

        try {

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "ffmpeg",
                    "-i",
                    "downloads/video.mp4",
                    "-vn",
                    "audio/audio.mp3"
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

            return exitCode == 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}