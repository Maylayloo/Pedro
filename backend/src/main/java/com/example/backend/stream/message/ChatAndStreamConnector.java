package com.example.backend.stream.message;

import com.example.backend.chat.repository.ChatMessageRepository;
import com.example.backend.stream.repo.StreamRepo;
import org.springframework.stereotype.Service;

@Service
public class ChatAndStreamConnector {
    private static ChatMessageRepository chatMessageRepository;
    private static StreamRepo streamRepository;
    public ChatAndStreamConnector(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }
    public static void deleteAllChatsByStreamId(String roomName) {
        try{
            Long streamId=streamRepository.findStreamIdByRoomName(roomName);
            chatMessageRepository.deleteAllByStreamId(streamId);
        }
        catch (Exception e){

        }

    }
}
