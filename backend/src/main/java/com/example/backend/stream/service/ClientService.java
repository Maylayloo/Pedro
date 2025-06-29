package com.example.backend.stream.service;

import io.github.cdimascio.dotenv.Dotenv;
import io.livekit.server.IngressServiceClient;
import io.livekit.server.RoomServiceClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ClientService {
    private final String apiKey;
    private final String apiSecret;
    private final String url;

    public ClientService() {
        Dotenv dotenv = Dotenv.configure()
                .filename("development.env")
                .load();
        this.apiKey = dotenv.get("LIVEKIT_API_KEY");
        this.apiSecret = dotenv.get("LIVEKIT_API_SECRET");
        this.url = dotenv.get("majneurl");
    }

    public RoomServiceClient getClient(){
        return RoomServiceClient.createClient(
                url,
                apiKey,
                apiSecret);
        };

    public IngressServiceClient getIngress(){
        return IngressServiceClient.createClient(url,apiKey,apiSecret);

    }
    public String getApiSecret() {
        return apiSecret;
    }
    public String getApiKey() {
        return apiKey;
    }
    public String getUrl() {
        return url;
    }
}
