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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;
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
    public void sendMessage(@DestinationVariable Long streamId,
                            ChatMessageRequestDTO messageDTO,
                            Principal principal) {
        System.out.println("Received message for stream: " + streamId);
        System.out.println("Principal: " + principal);

        if (principal instanceof Authentication auth) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Tutaj możesz użyć principal bez SecurityContextHolder:
        String email = principal.getName(); // lub rzutuj na Authentication jeśli chcesz

        Optional<MyUser> userOpt = userDataService.loadUserByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not authenticated, sadge");
        }

        chatMessageService.saveMessage(messageDTO, streamId);

        ChatMessageResponseDTO responseDTO = new ChatMessageResponseDTO(
                streamId,
                userOpt.get().getId(),
                userOpt.get().getNickname(),
                messageDTO.getMessage(),
                LocalDateTime.now()
        );
        messagingTemplate.convertAndSend("/topic/chat/" + streamId, responseDTO);
    }


}
