package com.example.backend.stream.mapper;

import com.example.backend.stream.dto.RoomDto;
import livekit.LivekitModels;

public class RoomMapper {
    public static RoomDto convertToDto(LivekitModels.Room room) {
        RoomDto dto = new RoomDto();
        dto.setEmptyTimeOut(room.getEmptyTimeout());
        dto.setRoomName(room.getName());
        // map other fields as needed
        return dto;
    }

}
