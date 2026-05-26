package com.youtube.transcriber.controller;

import com.youtube.transcriber.dto.ConvertRequest;
import com.youtube.transcriber.dto.ConvertResponse;

import com.youtube.transcriber.entity.Conversion;

import com.youtube.transcriber.repository.ConversionRepository;

import com.youtube.transcriber.service.AudioService;
import com.youtube.transcriber.service.PythonResult;
import com.youtube.transcriber.service.PythonService;
import com.youtube.transcriber.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ConvertController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private AudioService audioService;

    @Autowired
    private PythonService pythonService;

    @Autowired
    private ConversionRepository conversionRepository;

    @PostMapping("/convert")
    public ConvertResponse convertVideo(
            @RequestBody ConvertRequest request
    ) {

        try {

            // =====================================
            // STEP 1 : GET YOUTUBE URL
            // =====================================

            String url = request.getUrl();

            if (url == null || url.isEmpty()) {

                return new ConvertResponse(
                        "error",
                        "YouTube URL is required",
                        null,
                        null,
                        null,
                        null
                );
            }

            // =====================================
            // STEP 2 : DOWNLOAD VIDEO
            // =====================================

            String videoPath =
                    videoService.downloadVideo(url);

            if (videoPath == null) {

                return new ConvertResponse(
                        "error",
                        "Video download failed",
                        null,
                        null,
                        null,
                        null
                );
            }

            // =====================================
            // STEP 3 : EXTRACT AUDIO
            // =====================================

            String audioPath =
                    audioService.extractAudio(videoPath);

            if (audioPath == null) {

                return new ConvertResponse(
                        "error",
                        "Audio extraction failed",
                        null,
                        null,
                        null,
                        null
                );
            }

            // =====================================
            // STEP 4 : GENERATE TRANSCRIPT
            // =====================================

            PythonResult pythonResult =
                    pythonService.generateTranscript(audioPath);

            if (pythonResult == null) {

                return new ConvertResponse(
                        "error",
                        "Transcription failed",
                        null,
                        null,
                        null,
                        null
                );
            }

            // =====================================
            // STEP 5 : GET TRANSCRIPT DETAILS
            // =====================================

            String transcriptPath =
                    pythonResult.getTranscriptPath();

            String language =
                    pythonResult.getLanguage();

            // READ TRANSCRIPT TEXT
            String transcriptText =
                    Files.readString(
                            Paths.get(transcriptPath)
                    );

            // =====================================
            // STEP 6 : GENERATE FILE URLS
            // =====================================

            File audioFile = new File(audioPath);

            String audioUrl =
                    "http://localhost:8080/audio/"
                            + audioFile.getName();

            File transcriptFile =
                    new File(transcriptPath);

            String transcriptUrl =
                    "http://localhost:8080/transcripts/"
                            + transcriptFile.getName();

            // =====================================
            // STEP 7 : SAVE TO DATABASE
            // =====================================

            Conversion conversion =
                    new Conversion();

            conversion.setYoutubeUrl(url);

            conversion.setAudioUrl(audioUrl);

            conversion.setTranscriptUrl(transcriptUrl);

            conversion.setLanguage(language);

            conversion.setCreatedAt(LocalDateTime.now());

            conversionRepository.save(conversion);

            // =====================================
            // STEP 8 : SUCCESS RESPONSE
            // =====================================

            return new ConvertResponse(
                    "success",
                    "Transcript generated successfully",
                    audioUrl,
                    transcriptUrl,
                    transcriptText,
                    language
            );

        } catch (Exception e) {

            e.printStackTrace();

            return new ConvertResponse(
                    "error",
                    e.getMessage(),
                    null,
                    null,
                    null,
                    null
            );
        }
    }
}