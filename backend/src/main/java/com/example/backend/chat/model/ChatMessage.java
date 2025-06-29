package com.example.backend.chat.model;

import com.example.backend.chat.dto.ChatMessageResponseDTO;
import com.example.backend.stream.model.Stream;
import com.example.backend.user.model.MyUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "stream_id", nullable = false)
    private Stream stream;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private MyUser sender;

    private String content;
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }


    public ChatMessage() {
    }

    public ChatMessage(MyUser sender, String content, Stream stream) {
        this.sender = sender;
        this.content = content;
        this.stream = stream;
    }

    public ChatMessage(ChatMessageResponseDTO chatMessageResponseDTO, MyUser sender, Stream stream) {
        this.sender = sender;
        this.content = chatMessageResponseDTO.getContent();
        this.stream = stream;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    public MyUser getSender() {
        return sender;
    }

    public void setSender(MyUser sender) {
        this.sender = sender;
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