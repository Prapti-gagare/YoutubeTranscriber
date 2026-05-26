package com.youtube.transcriber.controller;

import com.youtube.transcriber.entity.Conversion;
import com.youtube.transcriber.repository.ConversionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HistoryController {

    @Autowired
    private ConversionRepository conversionRepository;

    @GetMapping("/history")
    public List<Conversion> getHistory() {

        return conversionRepository
                .findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/history/language/{language}")
    public List<Conversion> getByLanguage(
            @PathVariable String language
    ) {

        return conversionRepository
                .findByLanguage(language);
    }
    @DeleteMapping("/history/{id}")
public ResponseEntity<String> deleteConversion(
        @PathVariable Long id
) {

    Optional<Conversion> optionalConversion =
            conversionRepository.findById(id);

    if (optionalConversion.isEmpty()) {

        return ResponseEntity
                .badRequest()
                .body("Conversion not found");
    }

    Conversion conversion =
            optionalConversion.get();

    try {

        // DELETE AUDIO FILE
        String audioUrl =
                conversion.getAudioUrl();

        String audioFileName =
                audioUrl.substring(
                        audioUrl.lastIndexOf("/") + 1
                );

        File audioFile =
                new File(
                        "../audio/" + audioFileName
                );

        if (audioFile.exists()) {

            audioFile.delete();
        }

        // DELETE TRANSCRIPT FILE
        String transcriptUrl =
                conversion.getTranscriptUrl();

        String transcriptFileName =
                transcriptUrl.substring(
                        transcriptUrl.lastIndexOf("/") + 1
                );

        File transcriptFile =
                new File(
                        "../transcripts/" +
                                transcriptFileName
                );

        if (transcriptFile.exists()) {

            transcriptFile.delete();
        }

        // DELETE DATABASE RECORD
        conversionRepository.delete(conversion);

        return ResponseEntity.ok(
                "Conversion deleted successfully"
        );

    } catch (Exception e) {

        return ResponseEntity.internalServerError()
                .body(
                        "Delete failed: "
                                + e.getMessage()
                );
    }
}
}