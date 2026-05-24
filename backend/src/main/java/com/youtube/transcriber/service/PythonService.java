package com.youtube.transcriber.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class PythonService {

    public PythonResult generateTranscript(String audioPath) {

        try {

            System.out.println("STARTING PYTHON TRANSCRIPTION...");

            ProcessBuilder processBuilder = new ProcessBuilder(

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

            String language = null;

            while ((line = reader.readLine()) != null) {

                System.out.println("PYTHON OUTPUT: " + line);

                // GET TRANSCRIPT PATH
                if (line.startsWith("TRANSCRIPT_PATH=")) {

                    transcriptPath =
                            line.replace(
                                    "TRANSCRIPT_PATH=",
                                    ""
                            ).trim();
                }

                // GET DETECTED LANGUAGE
                if (line.startsWith("LANGUAGE=")) {

                    language =
                            line.replace(
                                    "LANGUAGE=",
                                    ""
                            ).trim();
                }
            }

            int exitCode = process.waitFor();

            System.out.println(
                    "PYTHON EXIT CODE: " + exitCode
            );

            if (exitCode == 0) {

                return new PythonResult(
                        transcriptPath,
                        language
                );
            }

            return null;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}