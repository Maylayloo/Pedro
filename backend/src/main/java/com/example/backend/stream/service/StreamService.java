package com.example.backend.stream.service;

import com.example.backend.stream.dto.StreamDto;
import com.google.protobuf.util.JsonFormat;
import io.livekit.server.IngressServiceClient;
import io.livekit.server.RoomServiceClient;
import livekit.LivekitIngress;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
@Service
public class StreamService {
    private final ClientService clientService;
    private final RoomServiceClient client;

    public StreamService(ClientService clientService, RoomServiceClient client) {
        this.clientService = clientService;
        this.client = client;
    }


    public Object createStream(StreamDto dto) throws IOException {
        IngressServiceClient ingressServiceClient=clientService.getIngress();
        Call<LivekitIngress.IngressInfo> ingressRequest= ingressServiceClient.createIngress(
                dto.getName(),dto.getRoomname(),dto.getParticipantIdentity(),
                "ingress"+dto.getParticipantName(),
                LivekitIngress.IngressInput.RTMP_INPUT,null,
                null,null,true,null);
        Response<LivekitIngress.IngressInfo> response=ingressRequest.execute();
        String json = JsonFormat.printer().print(response.body());
        return json;


    }

}
