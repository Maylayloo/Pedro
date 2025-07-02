package com.example.backend.chat.dto;

public class ChatSocketRequestDTO {
    private String type; // "chat" or "donate"
    private String message;
    private Integer amount; // only for donate messages


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
