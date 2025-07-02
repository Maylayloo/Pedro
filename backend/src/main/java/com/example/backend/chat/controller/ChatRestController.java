package com.example.backend.chat.controller;

import com.example.backend.chat.dto.ChatMessageResponseDTO;
import com.example.backend.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/history/{streamId}")
    public List<ChatMessageResponseDTO> getChatHistory(@PathVariable Long streamId) {
        return chatMessageService.getMessagesForStream(streamId);
    }
}

