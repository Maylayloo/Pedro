package com.example.backend.stream.service;

import com.example.backend.stream.dto.ObsDataDto;
import com.example.backend.stream.dto.RoomDto;
import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.dto.StreamRequestDto;
import com.example.backend.stream.exception.RoomNameAlreadyExistsException;
import com.example.backend.stream.mapper.StreamMapper;
import com.example.backend.stream.message.ChatAndStreamConnector;
import com.example.backend.stream.message.UserAndStreamConnectorService;
import com.example.backend.stream.model.Stream;
import com.example.backend.stream.repo.StreamRepo;
import io.livekit.server.IngressServiceClient;
import io.livekit.server.RoomServiceClient;
import jakarta.transaction.Transactional;
import livekit.LivekitIngress;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StreamService {
    private final ClientService clientService;
    private final RoomService roomService;
    private final StreamRepo repo;
    private final ParticipantService participantService;
    private final LivePedroCoinService livePedroCoinService;

    public StreamService(ClientService clientService,
                         RoomService roomService, StreamRepo repo, ParticipantService participantService,
                         LivePedroCoinService PedroCoinService ) {
        this.clientService = clientService;
        this.roomService = roomService;
        this.repo=repo;
        this.participantService=participantService;
        this.livePedroCoinService=PedroCoinService;
    }

    public ObsDataDto createStreamAndReturnObsData(StreamRequestDto dto) throws IOException {
        //UserAndStreamConnectorService.isUserInDatabase(dto.getUserId());

        checkIfRoomNameIsNotRepeating(dto.getRoomName());
        RoomDto room=new RoomDto(dto.getRoomName(),60,30);
        roomService.createRoom(room);
        IngressServiceClient ingressServiceClient=clientService.getIngress();
        Call<LivekitIngress.IngressInfo> ingressRequest= ingressServiceClient.createIngress(
                dto.getTitle(),dto.getRoomName(),"INGRESS",
                "ingress"+dto.getUserId(),
                LivekitIngress.IngressInput.RTMP_INPUT,null,
                LivekitIngress.IngressVideoOptions.newBuilder().getDefaultInstanceForType(),true,true,null);

        Response<LivekitIngress.IngressInfo> response=ingressRequest.execute();
        StreamDto result=new StreamDto(dto.getTitle(),dto.getDescription(),dto.getRoomName(),
                dto.getCategory(),System.currentTimeMillis(),response.body().getIngressId(),dto.getUserId());
        saveMetaData(result);
        return new ObsDataDto(response.body().getUrl(),response.body().getStreamKey());
    }

    public void saveMetaData(StreamDto dto){
        Stream stream=StreamMapper.toEntity(dto);
        repo.save(stream);
        livePedroCoinService.createLivePedroCoin(stream);
    }

    public List<StreamDto> getAllStreams() {
        List<Stream>streams=repo.findAll();
        return StreamMapper.toDtos(streams);
    }
    public List<Stream> getAll(){
        return repo.findAll();
    }

    public void checkIfRoomNameIsNotRepeating(String roomName) {
        List<StreamDto> streams = getAllStreams();
        boolean flag= streams.stream()
                .noneMatch(stream -> stream.getRoomName().equalsIgnoreCase(roomName));
        if(!flag){
            throw new RoomNameAlreadyExistsException("Room name already exists it the system , please provide different name");
        }
    }
    @Transactional
    public void deleteStreamByRoomName(String roomName) throws IOException {
        ChatAndStreamConnector.deleteAllChatsByStreamId(roomName);
        livePedroCoinService.removeByRoomName(roomName);
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

    public Stream getStreamByRoomName(String roomName) {
        return repo.findByRoomName(roomName));
    }

    public Long getCreationTimeByIngress(String ingress) {
        return repo.getCreationTimeByIngress(ingress)/1000;
    }

    public List<StreamDto> getTopThreePopularStreams() throws IOException {
        List<StreamDto> allStreams=StreamMapper.toDtos(repo.findAll());
        Map<StreamDto,Long> streamwithviewers=new HashMap<>();
        for(int i=0;i<allStreams.size();i++){
           streamwithviewers.put(allStreams.get(i),participantService.
                   getAllByRoomName(allStreams.get(i).getRoomName())
                   .stream().count());
        }
        return streamwithviewers.entrySet()
                .stream()
                .sorted(Map.Entry.<StreamDto, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Long getUserIdByRoomName(String roomName) {
        return repo.findByRoomName(roomName).getUserId();
    }

    public List<StreamDto> getStreamsByCategory(String category) {
        return StreamMapper.toDtos(
                repo.findAll().stream()
                        .filter(stream -> stream.getCategory().equalsIgnoreCase(category))
                        .collect(Collectors.toList())
        );
    }
}
