package com.example.backend.stream.controller;

import com.example.backend.stream.dto.RoomDto;
import com.example.backend.stream.mapper.RoomMapper;
import com.example.backend.stream.service.RoomService;
import livekit.LivekitModels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/room")
public class RoomController {




    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @PostMapping
    public ResponseEntity<Void> createRoom(@RequestBody RoomDto dto) throws IOException {
        roomService.createRoom(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> listRooms() throws IOException {
        List<LivekitModels.Room> rooms =roomService.listRooms();
        List<RoomDto> roomDtos = rooms.stream()
                .map(RoomMapper::convertToDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(roomDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{roomName}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomName) throws IOException {
        roomService.deleteRoomByRoomName(roomName);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
