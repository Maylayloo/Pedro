package com.example.backend.stream.controller;

import com.example.backend.stream.dto.ObsDataDto;
import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.dto.StreamRequestDto;
import com.example.backend.stream.model.Stream;
import com.example.backend.stream.service.LivePedroCoinService;
import com.example.backend.stream.service.SSeEmiterService;
import com.example.backend.stream.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/stream")
public class StreamController {
    private final StreamService streamService;
    private final SSeEmiterService sse;

    @Autowired
    private LivePedroCoinService livePedroCoinService;

    StreamController(StreamService streamService, SSeEmiterService sse) {
        this.streamService=streamService;
        this.sse=sse;
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
    public ResponseEntity<Stream> getStreamByRoomName(@PathVariable String roomName){
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

    @GetMapping("/sse/{roomName}")
    public SseEmitter streamUserCount(@PathVariable String roomName) throws IOException {
        return sse.countParticipantsByRoomName(roomName);
    }

    @GetMapping("/totalAmount/{streamId}")
    public ResponseEntity<Integer> getTotalAmountByRoomName(@PathVariable Long streamId) {
        int totalCoins = livePedroCoinService.getPedrocoinValueByStreamId(streamId);
        return new ResponseEntity<>(totalCoins, HttpStatus.OK);
    }
}

