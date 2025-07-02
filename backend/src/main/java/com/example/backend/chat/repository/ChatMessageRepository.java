package com.example.backend.chat.repository;

import com.example.backend.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByStreamId(Long streamId);
    List<ChatMessage> findTop50ByStreamIdOrderByTimestampDesc(Long streamId);
}
