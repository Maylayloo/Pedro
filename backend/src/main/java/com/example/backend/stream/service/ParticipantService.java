package com.example.backend.stream.service;

import io.livekit.server.RoomServiceClient;
import livekit.LivekitModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class ParticipantService {

    private final ClientService clientService;
    private final RoomServiceClient client;
    @Autowired
    public ParticipantService(ClientService clientService,RoomServiceClient client) {
        this.clientService = clientService;
        this.client=client;

    }

    public Call<List<LivekitModels.ParticipantInfo>> getAllByRoomName(String roomName) throws IOException {
        Call<List<LivekitModels.ParticipantInfo>> call = client.listParticipants(roomName);
        Response<List<LivekitModels.ParticipantInfo>> response = call.execute();
        return call;
    }

    public Call<LivekitModels.ParticipantInfo> getDetails(String roomName, String identity) throws IOException {
        Call<LivekitModels.ParticipantInfo> call = client.getParticipant(roomName,identity);
        Response<LivekitModels.ParticipantInfo> response = call.execute();
        return client.getParticipant(roomName,identity);
    }
}
