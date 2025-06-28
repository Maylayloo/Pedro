package com.example.backend.chat.service;

import com.example.backend.chat.dto.ChatMessageDTO;
import com.example.backend.chat.model.ChatMessage;
import com.example.backend.chat.repository.ChatMessageRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public void saveMessage(ChatMessageDTO messageDTO){
        ChatMessage chatMessage = new ChatMessage(messageDTO);
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageDTO> getMessagesForStream(Long streamId){
        return chatMessageRepository.findByStreamId(streamId).stream().
                map(ChatMessageDTO::new).collect(Collectors.toList());
    }
}
