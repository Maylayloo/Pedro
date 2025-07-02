package com.example.backend.chat.dto;

import java.time.LocalDateTime;

public class DonateMessageResponseDTO {
    private String type = "donate";
    private Long streamId;
    private Long userId;
    private String senderNickname;
    private String message;
    private int amount;
    private int totalAmount;
    private LocalDateTime timestamp;

    public DonateMessageResponseDTO(Long streamId, Long userId, String senderNickname, String message, int amount, int totalAmount, LocalDateTime now) {
        this.streamId = streamId;
        this.userId = userId;
        this.senderNickname = senderNickname;
        this.amount = amount;
        this.totalAmount = totalAmount;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
