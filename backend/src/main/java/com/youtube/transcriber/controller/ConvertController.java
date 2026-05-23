package com.youtube.transcriber.controller;

import com.youtube.transcriber.dto.ConvertRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ConvertController {

    @PostMapping("/convert")
    public String convertVideo(@RequestBody ConvertRequest request) {

        String youtubeUrl = request.getUrl();

        return "Received URL: " + youtubeUrl;
    }
}