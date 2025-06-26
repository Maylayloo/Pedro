package com.example.backend.stream.service;

import com.example.backend.stream.dto.TokenDto;
import io.livekit.server.AccessToken;
import io.livekit.server.RoomJoin;
import io.livekit.server.RoomName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LiveKitTokenService {
   @Autowired
   private ClientService clientService;

    public String getToken(TokenDto dto){
        AccessToken token = new AccessToken(clientService.getApiKey(), clientService.getApiSecret());
        token.addGrants(new RoomJoin(true),new RoomName(dto.getRoomName()));
        token.setName(dto.getParticipantName());
        token.setIdentity(dto.getIdentity());

        return token.toJwt();
    }

}
