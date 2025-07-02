package com.example.backend.chat.dto;

import com.example.backend.chat.model.ChatMessage;

import java.time.LocalDateTime;

public class ChatMessageResponseDTO {
    private Long streamId;
    private Long userId;
    private String senderNickname;
    private String content;
    private LocalDateTime timestamp;

    public ChatMessageResponseDTO() {}

    public ChatMessageResponseDTO(Long streamId, Long userId, String senderNickname, String content, LocalDateTime timestamp) {
        this.streamId = streamId;
        this.userId = userId;
        this.senderNickname = senderNickname;
        this.content = content;
        this.timestamp = timestamp;
    }

    public ChatMessageResponseDTO(ChatMessage message) {
        this.streamId = message.getStream().getId();
        this.userId = message.getSender().getId();
        this.senderNickname = message.getSender().getNickname();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
