package com.example.backend.stream.service;

import com.example.backend.stream.dto.ObsDataDto;
import com.example.backend.stream.dto.RoomDto;
import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.dto.StreamRequestDto;
import com.example.backend.stream.mapper.StreamMapper;
import com.example.backend.stream.message.UserAndStreamConnectorService;
import com.example.backend.stream.model.Stream;
import com.example.backend.stream.repo.StreamRepo;
import io.livekit.server.IngressServiceClient;
import io.livekit.server.RoomServiceClient;
import livekit.LivekitIngress;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@Slf4j
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

    public ObsDataDto createStreamAndReturnObsData(StreamRequestDto dto) throws IOException {
        //UserAndStreamConnectorService.isUserInDatabase(dto.getUserId());
        RoomDto room=new RoomDto(dto.getRoomName(),50000,30);
        roomService.createRoom(room);
        IngressServiceClient ingressServiceClient=clientService.getIngress();
        Call<LivekitIngress.IngressInfo> ingressRequest= ingressServiceClient.createIngress(
                dto.getTitle(),dto.getRoomName(),"INGRESS",
                "ingress"+dto.getUserId(),
                LivekitIngress.IngressInput.RTMP_INPUT,null,
                null,null,true,null);
        Response<LivekitIngress.IngressInfo> response=ingressRequest.execute();
        StreamDto result=new StreamDto(dto.getTitle(),dto.getDescription(),dto.getRoomName(),
                dto.getCategory(),System.currentTimeMillis(),response.body().getIngressId(),dto.getUserId());
        saveMetaData(result);
        return new ObsDataDto(response.body().getUrl(),response.body().getStreamKey());
    }



    public void saveMetaData(StreamDto dto){
        repo.save(StreamMapper.toEntity(dto));
    }


    public List<StreamDto> getAllStreams() {
        List<Stream>streams=repo.findAll();
        return StreamMapper.toDtos(streams);

    }

    public void deleteStreamByRoomName(String roomName) throws IOException {
        deleteIngress(roomName);
        roomService.deleteRoomByRoomName(roomName);

        repo.deleteByRoomName(roomName);
    }

    public void deleteIngress(String roomName) throws IOException {
            IngressServiceClient ingressServiceClient = clientService.getIngress();
            Call<List<LivekitIngress.IngressInfo>> ingressCall = ingressServiceClient.listIngress();
            Response<List<LivekitIngress.IngressInfo>> response = ingressCall.execute();
            for (LivekitIngress.IngressInfo info : response.body()) {
                if (info.getRoomName().equals(roomName)) {
                    Call<LivekitIngress.IngressInfo> deleteCall = ingressServiceClient.deleteIngress(info.getIngressId());
                    Response<LivekitIngress.IngressInfo> deleteResponse = deleteCall.execute();

                    if (deleteResponse.isSuccessful()) {
                        System.out.println("Successfully deleted ingress: " + info.getIngressId());
                    } else {
                        System.err.println("Failed to delete ingress: " + deleteResponse.errorBody().string());
                    }
                    return;
                }
            }
            System.out.println("No ingress found for room: " + roomName);
        }

    public StreamDto getStreamByRoomName(String roomName) {
        return StreamMapper.toDto(repo.findByRoomName(roomName));
    }


    public Long getCreationTimeByIngress(String ingress) {

        return repo.getCreationTimeByIngress(ingress)/1000;
    }
}
