package com.youtube.transcriber.dto;

public class ConvertResponse {

    private String status;
    private String message;
    private String audioUrl;
    private String transcriptPath;

    public ConvertResponse() {
    }

    public ConvertResponse(
            String status,
            String message,
            String audioUrl,
            String transcriptPath
    ) {
        this.status = status;
        this.message = message;
        this.audioUrl = audioUrl;
        this.transcriptPath = transcriptPath;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getTranscriptPath() {
        return transcriptPath;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setTranscriptPath(String transcriptPath) {
        this.transcriptPath = transcriptPath;
    }
}