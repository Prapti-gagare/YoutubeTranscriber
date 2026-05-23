package com.youtube.transcriber.controller;

import com.youtube.transcriber.dto.ConvertRequest;
import com.youtube.transcriber.dto.ConvertResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ConvertController {

   @PostMapping("/convert")
public ConvertResponse convertVideo(@RequestBody ConvertRequest request) {

    String youtubeUrl = request.getUrl();

    if (youtubeUrl == null || youtubeUrl.isEmpty()) {

        return new ConvertResponse(
                "error",
                "YouTube URL is required"
        );
    }

    return new ConvertResponse(
            "success",
            "Received URL: " + youtubeUrl
    );
}
}