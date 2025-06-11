package com.example.backend.stream.service;

import com.example.backend.stream.dto.TokenDto;
import io.livekit.server.AccessToken;
import io.livekit.server.RoomJoin;
import io.livekit.server.RoomName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LiveKitTokenService {
    @Value("${LIVEKIT_API_KEY}")
    private String apiKey;

    @Value("${LIVEKIT_API_SECRET}")
    private String apiSecret;

    public String getToken(TokenDto dto){
        AccessToken token = new AccessToken(apiKey, apiSecret);
        token.addGrants(new RoomJoin(true),new RoomName(dto.getRoomName()));
        token.setName(dto.getParticipantName());
        token.setIdentity(dto.getIdentity());

        return token.toJwt();
    }

}
