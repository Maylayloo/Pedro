package com.example.backend.stream.service;

import io.livekit.server.RoomServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Value("${LIVEKIT_API_KEY}")
    private String apiKey;

    @Value("${LIVEKIT_API_SECRET}")
    private String apiSecret;

    @Value("${LIVEKIT_URL")
    private String url;

    public RoomServiceClient getClient(){
        return RoomServiceClient.createClient(
                url,
                apiKey,
                apiSecret);
        };

    }
