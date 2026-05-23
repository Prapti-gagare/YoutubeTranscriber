package com.youtube.transcriber.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class VideoService {

    public boolean downloadVideo(String youtubeUrl) {

        try {

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "yt-dlp",
                    "-o",
                    "downloads/video.%(ext)s",
                    youtubeUrl
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