package com.youtube.transcriber.controller;

import com.youtube.transcriber.dto.ConvertRequest;
import com.youtube.transcriber.dto.ConvertResponse;
import com.youtube.transcriber.service.AudioService;
import com.youtube.transcriber.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ConvertController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private AudioService audioService;

    @PostMapping("/convert")
    public ConvertResponse convertVideo(@RequestBody ConvertRequest request) {

        String youtubeUrl = request.getUrl();

        if (youtubeUrl == null || youtubeUrl.isEmpty()) {

            return new ConvertResponse(
                    "error",
                    "YouTube URL is required"
            );
        }

        boolean downloaded = videoService.downloadVideo(youtubeUrl);

        if (!downloaded) {

            return new ConvertResponse(
                    "error",
                    "Video download failed"
            );
        }

        boolean audioExtracted = audioService.extractAudio();

        if (!audioExtracted) {

            return new ConvertResponse(
                    "error",
                    "Audio extraction failed"
            );
        }

        return new ConvertResponse(
                "success",
                "Audio extracted successfully"
        );
    }
}