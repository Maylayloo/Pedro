package com.example.backend.stream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamDto {
    String name;
    String roomName;
    String participantIdentity;
    String participantName;
    String title;
    String descryption;
    String category;
    Long userId;

}
