package com.example.backend.stream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private String roomName;
    private int emptyTimeOut;
    private int  maxParticipant;
    public RoomDto(){};
    public RoomDto(String roomName, int emptyTimeOut, int maxParticipant) {
        this.roomName = roomName;
        this.emptyTimeOut = emptyTimeOut;
        this.maxParticipant = maxParticipant;
    }
}
