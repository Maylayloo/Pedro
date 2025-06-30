package com.example.backend.stream.mapper;

import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.model.Stream;

import java.util.List;
import java.util.stream.Collectors;

public class StreamMapper {
    public static Stream toEntity(StreamDto dto) {
        Stream stream = new Stream();
        stream.setTitle(dto.getTitle());
        stream.setDescription(dto.getDescription());
        stream.setUserId(dto.getUserId());
        stream.setRoomName(dto.getRoomName());
        stream.setCreationTime(dto.getCreationTime());
        stream.setCategory(dto.getCategory());
        stream.setIngressId(dto.getIngressId());
        return stream;
    }

    public static List<StreamDto> toDtos(List<Stream> streams){
        return streams.stream()
                .map(StreamMapper::toDto)
                .collect(Collectors.toList());
    }

    public static StreamDto toDto(Stream stream){
        StreamDto dto = new StreamDto();
        dto.setTitle(stream.getTitle());
        dto.setDescription(stream.getDescription());
        dto.setRoomName(stream.getRoomName());
        dto.setCategory(stream.getCategory());
        dto.setUserId(stream.getUserId());
        dto.setCreationTime(stream.getCreationTime());
        dto.setIngressId(stream.getIngressId());
        return dto;
    }
}
