package com.example.backend.stream.controller;

import com.example.backend.stream.dto.RoomDto;
import com.example.backend.stream.service.RoomService;
import livekit.LivekitModels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {




    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomDto dto) throws IOException {
        roomService.createRoom(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listRooms() throws IOException {
        List<LivekitModels.Room> rooms =roomService.listRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    @DeleteMapping("/{roomName}")
    public ResponseEntity<?> deleteRoom(@RequestParam String roomName) throws IOException {
        roomService.deleteRoomByRoomName(roomName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
