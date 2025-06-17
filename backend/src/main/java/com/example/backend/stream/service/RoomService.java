package com.example.backend.stream.service;

import com.example.backend.stream.dto.RoomDto;
import com.google.protobuf.util.JsonFormat;
import io.livekit.server.RoomServiceClient;
import livekit.LivekitModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class RoomService {
    private final ClientService clientService;
    private final RoomServiceClient client;

    public RoomService(ClientService clientService,RoomServiceClient client) {
        this.clientService = clientService;
        this.client=client;

    }
    public void createRoom(RoomDto dto) throws IOException {
        Call<LivekitModels.Room> call = client.createRoom(dto.getRoomName(),dto.getEmptyTimeOut(),dto.getMaxParticipant());
        Response<LivekitModels.Room> response = call.execute();
    }

    public List<LivekitModels.Room> listRooms() throws IOException {
        Call<List<LivekitModels.Room>> call = client.listRooms();
        Response<List<LivekitModels.Room>> response = call.execute();
        List<LivekitModels.Room> rooms = response.body();
        return rooms;
    }

    public void deleteRoomByRoomName(String name) throws IOException {
        Call<Void> call = client.deleteRoom(name);
        Response<Void> response = call.execute();
    }
}
