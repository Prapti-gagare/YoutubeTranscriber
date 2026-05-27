package com.youtube.transcriber.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String youtubeUrl;

    private String audioUrl;

    private String transcriptUrl;

    private String language;

    private LocalDateTime createdAt;

    public Conversion() {
    }

    public Long getId() {
        return id;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getTranscriptUrl() {
        return transcriptUrl;
    }

    public String getLanguage() {
        return language;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public void setTranscriptUrl(String transcriptUrl) {
        this.transcriptUrl = transcriptUrl;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}