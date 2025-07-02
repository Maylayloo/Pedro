package com.example.backend.chat.controller;

import com.example.backend.chat.dto.ChatMessageRequestDTO;
import com.example.backend.chat.dto.ChatMessageResponseDTO;
import com.example.backend.chat.dto.ChatSocketRequestDTO;
import com.example.backend.chat.dto.DonateMessageResponseDTO;
import com.example.backend.chat.service.ChatMessageService;
import com.example.backend.stream.service.LivePedroCoinService;
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

    @Autowired
    private LivePedroCoinService livePedroCoinService;

    @MessageMapping("/chat/{streamId}")
    public void handleSocketMessage(@DestinationVariable Long streamId,
                                    ChatSocketRequestDTO requestDTO,
                                    Principal principal) {
        if (principal instanceof Authentication auth) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        String email = principal.getName();
        Optional<MyUser> userOpt = userDataService.loadUserByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not authenticated, sadge");
        }

        MyUser sender = userOpt.get();

        switch (requestDTO.getType()) {
            case "chat" -> handleChatMessage(requestDTO, streamId, sender);
            case "donate" -> handleDonateMessage(requestDTO, streamId, sender);
            default -> throw new IllegalArgumentException("Unknown message type: " + requestDTO.getType());
        }
    }

    private void handleChatMessage(ChatSocketRequestDTO dto, Long streamId, MyUser sender) {
        chatMessageService.saveMessage(new ChatMessageRequestDTO(dto.getMessage()), streamId);

        ChatMessageResponseDTO response = new ChatMessageResponseDTO(
                streamId,
                sender.getId(),
                sender.getNickname(),
                dto.getMessage(),
                LocalDateTime.now()
        );
        messagingTemplate.convertAndSend("/topic/chat/" + streamId, response);
    }

    private void handleDonateMessage(ChatSocketRequestDTO dto, Long streamId, MyUser sender) {
        int amount = dto.getAmount();

        livePedroCoinService.donateStreamerWithcoinAmountByStreamId(streamId, amount);

        int totalCoins = livePedroCoinService.getPedrocoinValueByStreamId(streamId);

        DonateMessageResponseDTO donateResponse = new DonateMessageResponseDTO(
                streamId,
                sender.getId(),
                sender.getNickname(),
                dto.getMessage(),
                amount,
                totalCoins,
                LocalDateTime.now()
        );

        messagingTemplate.convertAndSend("/topic/chat/" + streamId, donateResponse);
    }



}
