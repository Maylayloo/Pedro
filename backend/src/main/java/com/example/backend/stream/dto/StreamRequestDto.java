package com.example.backend.stream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamRequestDto {
    String title;
    String descryption;
    String roomName;
    String category;
    Long userId;
}
