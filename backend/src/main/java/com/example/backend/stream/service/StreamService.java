package com.example.backend.stream.service;

import com.example.backend.stream.dto.ObsDataDto;
import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.mapper.StreamMapper;
import com.example.backend.stream.model.Stream;
import com.example.backend.stream.repo.StreamRepo;
import io.livekit.server.IngressServiceClient;
import io.livekit.server.RoomServiceClient;
import livekit.LivekitIngress;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Service
public class StreamService {
    private final ClientService clientService;
    private final RoomService roomService;
    private final StreamRepo repo;
    public StreamService(ClientService clientService, RoomService roomService, StreamRepo repo) {
        this.clientService = clientService;
        this.roomService = roomService;
        this.repo=repo;
    }


    public ObsDataDto createStreamAndReturnObsData(StreamDto dto) throws IOException {
        IngressServiceClient ingressServiceClient=clientService.getIngress();
        Call<LivekitIngress.IngressInfo> ingressRequest= ingressServiceClient.createIngress(
                dto.getName(),dto.getRoomName(),dto.getParticipantIdentity(),
                "ingress"+dto.getParticipantName(),
                LivekitIngress.IngressInput.RTMP_INPUT,null,
                null,null,true,null);
        Response<LivekitIngress.IngressInfo> response=ingressRequest.execute();
        saveMetaData(dto);
        return new ObsDataDto(response.body().getUrl(),response.body().getStreamKey());
    }

    public void saveMetaData(StreamDto dto){
        repo.save(StreamMapper.toEntity(dto));
    }


    public List<StreamDto> getAllStreams() {
        List<Stream>streams=repo.findAll();
        return StreamMapper.toDtos(streams);

    }

    public void deleteStreamByStreamName(String roomName) throws IOException {
        roomService.deleteRoomByRoomName(roomName);
        repo.deleteByRoomName(roomName);
    }


    public StreamDto getStreamByRoomName(String roomName) {
        return repo.findByRoomName(roomName);
    }
}
