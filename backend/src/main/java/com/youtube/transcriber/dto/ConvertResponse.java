package com.youtube.transcriber.dto;

public class ConvertResponse {

    private String status;
    private String message;
    private String audioUrl;
    private String transcriptUrl;
    private String transcriptText;
    private String language;

    public ConvertResponse(
            String status,
            String message,
            String audioUrl,
            String transcriptUrl,
            String transcriptText,
            String language
    ) {
        this.status = status;
        this.message = message;
        this.audioUrl = audioUrl;
        this.transcriptUrl = transcriptUrl;
        this.transcriptText = transcriptText;
        this.language = language;
    }

    // GETTERS

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getTranscriptUrl() {
        return transcriptUrl;
    }

    public String getTranscriptText() {
        return transcriptText;
    }

    public String getLanguage() {
        return language;
    }

    // SETTERS

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setTranscriptUrl(String transcriptUrl) {
        this.transcriptUrl = transcriptUrl;
    }

    public void setTranscriptText(String transcriptText) {
        this.transcriptText = transcriptText;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}