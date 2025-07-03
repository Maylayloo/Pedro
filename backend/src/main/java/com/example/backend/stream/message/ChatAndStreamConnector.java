package com.example.backend.stream.message;

import com.example.backend.chat.repository.ChatMessageRepository;
import com.example.backend.stream.repo.StreamRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatAndStreamConnector {
    private static ChatMessageRepository chatMessageRepository;
    private static StreamRepo streamRepository;
    public ChatAndStreamConnector(ChatMessageRepository chatMessageRepository, StreamRepo streamRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.streamRepository = streamRepository;
    }

    public void deleteAllChatsByStreamId(String roomName) {
        try{
            System.out.println("deletet fully chat!!!");
            Long streamId=streamRepository.findStreamIdByRoomName(roomName);
            System.out.println("streamId: "+streamId);
            chatMessageRepository.deleteAllByStreamId(streamId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
