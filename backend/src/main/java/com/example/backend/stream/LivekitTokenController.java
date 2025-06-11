package com.example.backend.stream;

import com.example.backend.stream.dto.TokenDto;
import com.google.protobuf.util.JsonFormat;
import io.livekit.server.*;
import livekit.LivekitModels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@RestController
public class LivekitTokenController {

    @Value("${LIVEKIT_API_KEY}")
    private String apiKey;

    @Value("${LIVEKIT_API_SECRET}")
    private String apiSecret;

    @GetMapping("/getToken")
    public String getToken(@RequestBody TokenDto dto) throws IOException {

        AccessToken token = new AccessToken(apiKey, apiSecret);
        token.addGrants(new RoomJoin(true),new RoomName(dto.getRoomName()));
        token.setName(dto.getParticipantName());
        token.setIdentity(dto.getIdentity());

        return token.toJwt();
    }
}
