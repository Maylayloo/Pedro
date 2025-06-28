package com.example.backend.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamDto {
    String title;
    String descryption;
    String roomName;
    String category;
    Long creationTime;
    String ingressId;
    Long userId;
    public StreamDto(){}
    public StreamDto(String title, String descryption,
                     String roomName, String category, Long creationTime, String ingressId, Long userId) {
        this.title = title;
        this.descryption = descryption;
        this.roomName = roomName;
        this.category = category;
        this.creationTime = creationTime;
        this.ingressId = ingressId;
        this.userId = userId;
    }
}
