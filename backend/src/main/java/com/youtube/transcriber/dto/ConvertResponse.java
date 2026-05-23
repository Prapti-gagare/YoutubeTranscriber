package com.youtube.transcriber.dto;

public class ConvertResponse {

    private String status;
    private String message;

    public ConvertResponse() {
    }

    public ConvertResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}