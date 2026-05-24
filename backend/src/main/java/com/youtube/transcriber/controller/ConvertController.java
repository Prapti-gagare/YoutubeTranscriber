package com.youtube.transcriber.controller;

import com.youtube.transcriber.dto.ConvertRequest;
import com.youtube.transcriber.dto.ConvertResponse;
import com.youtube.transcriber.service.AudioService;
import com.youtube.transcriber.service.PythonService;
import com.youtube.transcriber.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api")
public class ConvertController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private AudioService audioService;

    @Autowired
    private PythonService pythonService;

    @PostMapping("/convert")
    public ConvertResponse convertVideo(@RequestBody ConvertRequest request) {

        String url = request.getUrl();

        if (url == null || url.isEmpty()) {

            return new ConvertResponse(
                    "error",
                    "YouTube URL is required",
                    null,
                    null
            );
        }

        // STEP 1 — DOWNLOAD VIDEO
        String videoPath = videoService.downloadVideo(url);

        if (videoPath == null) {

            return new ConvertResponse(
                    "error",
                    "Video download failed",
                    null,
                    null
            );
        }

        // STEP 2 — EXTRACT AUDIO
        String audioPath = audioService.extractAudio(videoPath);

        if (audioPath == null) {

            return new ConvertResponse(
                    "error",
                    "Audio extraction failed",
                    null,
                    null
            );
        }

        // STEP 3 — GENERATE TRANSCRIPT
        String transcriptPath =
                pythonService.generateTranscript(audioPath);

        if (transcriptPath == null) {

            return new ConvertResponse(
                    "error",
                    "Transcript generation failed",
                    null,
                    null
            );
        }

        // AUDIO URL
        File audioFile = new File(audioPath);

        String audioUrl =
                "http://localhost:8080/audio/" +
                        audioFile.getName();

        return new ConvertResponse(
                "success",
                "Transcript generated successfully",
                audioUrl,
                transcriptPath
        );
    }
}