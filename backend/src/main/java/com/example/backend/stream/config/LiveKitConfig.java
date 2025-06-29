package com.example.backend.stream.config;

import com.example.backend.stream.service.ClientService;
import io.livekit.server.IngressServiceClient;
import io.livekit.server.RoomServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiveKitConfig {
    @Autowired
    private ClientService clientService;
    @Bean
    public RoomServiceClient roomServiceClient() {
        return RoomServiceClient.createClient(clientService.getUrl(),clientService.getApiKey(), clientService.getApiSecret());
    }
    @Bean
    public IngressServiceClient ingressServiceClient(){
        return IngressServiceClient.createClient(clientService.getUrl(), clientService.getApiKey(), clientService.getApiSecret());

    }

}