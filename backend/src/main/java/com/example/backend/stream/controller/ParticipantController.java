package com.example.backend.stream.controller;

import com.example.backend.stream.dto.ParticipantDto;
import com.example.backend.stream.service.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    private ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<?> listParticipants(@RequestParam String roomName) throws IOException {
        return new ResponseEntity<>(participantService.getAllByRoomName(roomName), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipant(@RequestBody ParticipantDto dto ) throws IOException {
        return new ResponseEntity<>(
                participantService.getDetails(dto.getParticipantName(),dto.getIdentity()),HttpStatus.OK);
    }


}
