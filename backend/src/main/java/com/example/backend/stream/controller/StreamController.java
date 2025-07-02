package com.example.backend.stream.controller;

import com.example.backend.stream.dto.ObsDataDto;
import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.dto.StreamRequestDto;
import com.example.backend.stream.service.StreamService;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stream")
public class StreamController {
    private final StreamService streamService;

    StreamController(StreamService streamService){
        this.streamService=streamService;
    }

    @PostMapping
    public ResponseEntity<ObsDataDto> createStream(@RequestBody StreamRequestDto dto) throws IOException {
        return new ResponseEntity<>(streamService.createStreamAndReturnObsData(dto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StreamDto>> getAllStreams(){
        return new ResponseEntity<>(streamService.getAllStreams(), HttpStatus.OK);
    }

    @DeleteMapping("/{roomName}")
    public ResponseEntity<Void> deleteStream(@PathVariable String roomName) throws IOException {
        streamService.deleteStreamByRoomName(roomName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{roomName}")
    public ResponseEntity<StreamDto> getStreamByRoomName(@PathVariable String roomName){
        return new ResponseEntity<>(streamService.getStreamByRoomName(roomName),HttpStatus.OK);
    }

    @GetMapping("/top3")
    public ResponseEntity<List<StreamDto>> getTop3Streams() throws IOException {
        return new ResponseEntity<>(streamService.getTopThreePopularStreams(),HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<StreamDto>> getSteamsByCategory(@PathVariable String category) throws IOException {
        return new ResponseEntity<>(streamService.getStreamsByCategory(category),HttpStatus.OK);
    }
    

}

