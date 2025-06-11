package com.example.backend.stream.controller;

import com.example.backend.stream.dto.TokenDto;
import com.example.backend.stream.service.LiveKitTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LivekitTokenController {

    @Value("${LIVEKIT_API_KEY}")
    private String apiKey;

    @Value("${LIVEKIT_API_SECRET}")
    private String apiSecret;

    private LiveKitTokenService tokenService;
    @GetMapping("/getToken")
    public String getToken(@RequestBody TokenDto dto) throws IOException {
        tokenService.getToken(dto);
    }

}
