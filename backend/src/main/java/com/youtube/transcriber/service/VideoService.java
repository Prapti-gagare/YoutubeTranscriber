package com.youtube.transcriber.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class VideoService {

    public String downloadVideo(String youtubeUrl) {

        try {

            // CREATE DOWNLOADS FOLDER
            File downloadFolder = new File("downloads");

            if (!downloadFolder.exists()) {
                downloadFolder.mkdirs();
            }

            // UNIQUE FILE NAME
            long timestamp = System.currentTimeMillis();

            String outputTemplate =
                    "downloads/video_" + timestamp + ".%(ext)s";

            // UPDATED YT-DLP COMMAND
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "yt-dlp",

                    "--no-check-certificates",

                    "--extractor-args",
                    "youtube:player_client=android",

                    "--user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/124.0 Safari/537.36",

                    "-f",
                    "mp4",

                    "-o",
                    outputTemplate,

                    youtubeUrl
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // READ OUTPUT
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;

            while ((line = reader.readLine()) != null) {

                System.out.println("YT-DLP: " + line);
            }

            int exitCode = process.waitFor();

            System.out.println("YT-DLP EXIT CODE: " + exitCode);

            // FAILED
            if (exitCode != 0) {

                return null;
            }

            // FIND DOWNLOADED FILE
            File[] files = downloadFolder.listFiles();

            if (files == null) {

                return null;
            }

            String expectedName = "video_" + timestamp;

            for (File file : files) {

                if (file.getName().startsWith(expectedName)) {

                    System.out.println(
                            "VIDEO DOWNLOADED: "
                                    + file.getAbsolutePath()
                    );

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