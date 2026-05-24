package com.youtube.transcriber.service;

public class PythonResult {

    private String transcriptPath;
    private String language;

    public PythonResult(
            String transcriptPath,
            String language
    ) {
        this.transcriptPath = transcriptPath;
        this.language = language;
    }

    public String getTranscriptPath() {
        return transcriptPath;
    }

    public String getLanguage() {
        return language;
    }
}