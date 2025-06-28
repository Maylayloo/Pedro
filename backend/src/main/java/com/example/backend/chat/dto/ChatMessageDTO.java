package com.example.backend.chat.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

public class ChatMessageDTO {
    private Long streamId;
    private Long userId;
    private String senderNickname;
    private String content;
    private LocalDateTime timestamp;

    public ChatMessageDTO() {}

    public ChatMessageDTO(Long streamId, Long userId, String senderNickname, String content, LocalDateTime timestamp) {
        this.streamId = streamId;
        this.userId = userId;
        this.senderNickname = senderNickname;
        this.content = content;
        this.timestamp = timestamp;
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
