package com.example.backend.stream.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.Executors;

@Service
public class SSeEmiterService {
    private ParticipantService participantService;
    SSeEmiterService(ParticipantService participantService) {
        this.participantService = participantService;
    }
    public SseEmitter countParticipantsByRoomName(String roomName){
        SseEmitter emitter = new SseEmitter();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (true) {
                    Long userCount = participantService.getAllByRoomName(roomName).stream().count() ;
                    emitter.send(SseEmitter.event()
                            .name("user-count")
                            .data(userCount));
                    Thread.sleep(2000); // odświeżanie co 2 sekundy
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
