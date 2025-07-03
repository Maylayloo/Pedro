package com.example.backend.chat.repository;

import com.example.backend.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByStreamId(Long streamId);
    List<ChatMessage> findTop50ByStreamIdOrderByTimestampDesc(Long streamId);
    @Modifying
    @Transactional
    @Query("DELETE FROM ChatMessage c WHERE c.stream.id = :streamId")
    void deleteAllByStreamId(@Param("streamId") Long streamId);
}
