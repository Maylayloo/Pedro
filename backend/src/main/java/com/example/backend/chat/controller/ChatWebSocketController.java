package com.example.backend.chat.controller;

import com.example.backend.chat.dto.ChatMessageRequestDTO;
import com.example.backend.chat.dto.ChatMessageResponseDTO;
import com.example.backend.chat.service.ChatMessageService;
import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.MyUserRepository;
import com.example.backend.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private UserDataService userDataService;

    @MessageMapping("/chat/{streamId}")
    @SendTo("/topic/chat/{streamId}")
    public void sendMessage(@DestinationVariable Long streamId, ChatMessageRequestDTO messageDTO) {

        chatMessageService.saveMessage(messageDTO, streamId);

        Optional<MyUser> userOpt = userDataService.getLoggedInUser();

        ChatMessageResponseDTO responseDTO = new ChatMessageResponseDTO(
                streamId,
                userOpt.map(MyUser::getId).orElse(null),
                messageDTO.getMessage(),
                userOpt.get().getNickname(),
                LocalDateTime.now()
        );
    }

}
