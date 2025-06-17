package com.example.backend.stream.mapper;

import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.model.Stream;

import java.util.List;
import java.util.stream.Collectors;

public class StreamMapper {
    public static Stream toEntity(StreamDto dto){
        return Stream.builder()
                .title(dto.getTitle())
                .descryption(dto.getDescryption())
                .userId(dto.getUserId())
                .roomName(dto.getRoomName())
                .build();

    }

    public static List<StreamDto> toDtos(List<Stream> streams){
        return streams.stream()
                .map(stream -> {
                    StreamDto dto = new StreamDto();
                    dto.setTitle(stream.getTitle());
                    dto.setDescryption(stream.getDescryption());
                    dto.setRoomName(stream.getRoomName());
                    dto.setCategory(stream.getCategory());
                    dto.setUserId(stream.getUserId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
