package com.example.backend.chat.dto;

public class ChatMessageRequestDTO {
    private String message;

    public ChatMessageRequestDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
