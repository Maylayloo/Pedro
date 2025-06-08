package com.example.backend.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stream/")
public class StreamController {

    @GetMapping
    public ResponseEntity<Map<String,String>> getAuthentication() throws JsonProcessingException {
        return new ResponseEntity<>(LiveKitUtil.createToken(), HttpStatus.OK);
    }
}
