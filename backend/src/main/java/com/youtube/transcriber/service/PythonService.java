package com.youtube.transcriber.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class PythonService {

    public String generateTranscript(String audioPath) {

        try {

            System.out.println("STARTING PYTHON TRANSCRIPTION...");

            ProcessBuilder processBuilder = new ProcessBuilder(

                    // IMPORTANT:
                    // REPLACE THIS WITH YOUR REAL PYTHON PATH

                    "C:\\Program Files\\Python313\\python.exe",

                    "../python/transcribe.py",

                    audioPath
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;

            String transcriptPath = null;

            while ((line = reader.readLine()) != null) {

                System.out.println("PYTHON OUTPUT: " + line);

                if (line.startsWith("TRANSCRIPT_PATH=")) {

                    transcriptPath =
                            line.replace("TRANSCRIPT_PATH=", "").trim();
                }
            }

            int exitCode = process.waitFor();

            System.out.println("PYTHON EXIT CODE: " + exitCode);

            if (exitCode == 0) {

                return transcriptPath;
            }

            return null;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}