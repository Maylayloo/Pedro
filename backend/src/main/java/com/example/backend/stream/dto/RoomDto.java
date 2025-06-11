package com.example.backend.stream.dto;

import lombok.Getter;

@Getter
public class RoomDto {
    private String roomName;
    private int emptyTimeOut;
    private int  maxParticipant;


}
