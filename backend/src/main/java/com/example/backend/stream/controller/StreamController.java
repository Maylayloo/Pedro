package com.example.backend.stream.controller;

import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.service.StreamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/stream")
public class StreamController {
    private StreamService streamService;
    StreamController(StreamService streamService){
        this.streamService=streamService;
    }
    @PostMapping
    public ResponseEntity<?> createStream(@RequestBody StreamDto dto) throws IOException {
        return new ResponseEntity<>(streamService.createStream(dto), HttpStatus.OK);
    }
}
