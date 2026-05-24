package com.youtube.transcriber.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/transcripts")
public class TranscriptController {

    private final Path transcriptFolder =
            Paths.get("../transcripts")
                    .toAbsolutePath()
                    .normalize();

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getTranscript(
            @PathVariable String fileName
    ) {

        try {

            Path filePath =
                    transcriptFolder.resolve(fileName)
                            .normalize();

            Resource resource =
                    new UrlResource(filePath.toUri());

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_TYPE,
                            "text/plain; charset=UTF-8"
                    )
                    .body(resource);

        } catch (MalformedURLException e) {

            return ResponseEntity.notFound().build();
        }
    }
}