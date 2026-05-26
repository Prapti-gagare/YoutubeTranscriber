package com.youtube.transcriber.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class VideoService {

    public String downloadVideo(String youtubeUrl) {

        try {

            // UNIQUE ID using timestamp
            long timestamp = System.currentTimeMillis();

            String outputTemplate = "downloads/video_" + timestamp + ".%(ext)s";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "yt-dlp",
                    "-f",
                    "best",
                    "-o",
                    outputTemplate,
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

            if (exitCode != 0) {
                return null;
            }

            // FIND ACTUAL FILE
            File folder = new File("downloads");

            File[] files = folder.listFiles();

            if (files == null) return null;

            String expectedName = "video_" + timestamp;

            for (File file : files) {

                if (file.getName().startsWith(expectedName)) {

                    return file.getAbsolutePath();
                }
            }

            return null;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}