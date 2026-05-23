package com.youtube.transcriber.controller;

import com.youtube.transcriber.dto.ConvertRequest;
import com.youtube.transcriber.dto.ConvertResponse;
import com.youtube.transcriber.service.AudioService;
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

    @PostMapping("/convert")
    public ConvertResponse convertVideo(@RequestBody ConvertRequest request) {

        String url = request.getUrl();

        if (url == null || url.isEmpty()) {

            return new ConvertResponse(
                    "error",
                    "YouTube URL is required",
                    null
            );
        }

        // STEP 1: DOWNLOAD VIDEO
        String videoPath = videoService.downloadVideo(url);

        if (videoPath == null) {

            return new ConvertResponse(
                    "error",
                    "Video download failed",
                    null
            );
        }

        // STEP 2: EXTRACT AUDIO
        String audioPath = audioService.extractAudio(videoPath);

        if (audioPath == null) {

            return new ConvertResponse(
                    "error",
                    "Audio extraction failed",
                    null
            );
        }

        // STEP 3: GENERATE AUDIO URL

        File audioFile = new File(audioPath);

        String fileName = audioFile.getName();

        String audioUrl =
                "http://localhost:8080/audio/" + fileName;

        return new ConvertResponse(
                "success",
                "Audio generated successfully",
                audioUrl
        );
    }
}