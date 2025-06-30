package com.example.backend.chat.service;

import com.example.backend.chat.dto.ChatMessageRequestDTO;
import com.example.backend.chat.dto.ChatMessageResponseDTO;
import com.example.backend.chat.model.ChatMessage;
import com.example.backend.chat.repository.ChatMessageRepository;
import com.example.backend.stream.model.Stream;
import com.example.backend.stream.repo.StreamRepo;
import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.MyUserRepository;
import com.example.backend.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserDataService myUserService;

    @Autowired
    private StreamRepo streamRepo;

    public void saveMessage(ChatMessageRequestDTO requestDTO, Long streamId) {
        MyUser user = myUserService.getLoggedInUser()
                .orElseThrow(() -> new RuntimeException("User not authenticated, sadge"));

        List<Stream> streams = streamRepo.findAll();
        System.out.println("Available streams: " + streams.size());

        for (Stream stream : streams) {
            System.out.println("Stream ID: " + stream.getId());
        }

        Stream stream = streamRepo.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        ChatMessage message = new ChatMessage(
                user,
                requestDTO.getMessage(),
                stream
        );
        chatMessageRepository.save(message);
    }

    public List<ChatMessageResponseDTO> getMessagesForStream(Long streamId){
        return chatMessageRepository.findTop50ByStreamIdOrderByTimestampDesc(streamId).stream().
                map(ChatMessageResponseDTO::new).collect(Collectors.toList());
    }
}
