package com.example.backend.chat.model;

import com.example.backend.chat.dto.ChatMessageDTO;
import com.example.backend.user.model.MyUser;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long messageId;

    //@ManyToOne
    //@JoinColumn(name = "stream_id", nullable = false)
    //private Stream stream

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private MyUser sender;

    private String content;
    private LocalDateTime timestamp;

    public ChatMessage() {
    }

    public ChatMessage(MyUser sender, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public ChatMessage(ChatMessageDTO chatMessageDTO, MyUser sender) {  //Stream stream
        this.sender = new MyUser();
        this.content = chatMessageDTO.getContent();
        this.timestamp = chatMessageDTO.getTimestamp();
    }

}