package com.example.backend.stream.service;

import com.example.backend.stream.message.UserAndStreamConnectorService;
import io.livekit.server.IngressServiceClient;
import livekit.LivekitIngress;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StreamScheduler {
    private final ClientService clientService;
    private final StreamService streamService;
    private final int FIXED_DELAY=60*1000;
    public StreamScheduler(ClientService clientService, StreamService streamService) {
        this.clientService = clientService;
        this.streamService = streamService;
    }


    @Scheduled(fixedDelay = FIXED_DELAY)
    public void cleanUpOldIngresses() throws IOException {
        IngressServiceClient client = clientService.getIngress();
        List<LivekitIngress.IngressInfo> ingresses = client.listIngress().execute().body();
        long nowEpochSeconds = System.currentTimeMillis() / 1000;
        long fiveMinutesInSeconds = 60;
        for (LivekitIngress.IngressInfo ingress : ingresses) {
            boolean isInactive = ingress.getState().getStatus() == LivekitIngress.IngressState.Status.ENDPOINT_INACTIVE;
            System.out.println("deleteing stream with room name:"+ingress.getRoomName());
            long createdAt=0;
            try{
                createdAt = streamService.getCreationTimeByIngress(ingress.getIngressId());
            }
            catch (Exception e){
                streamService.deleteIngress(ingress.getRoomName());
            }
            boolean isOlderThan1Min = nowEpochSeconds - createdAt> fiveMinutesInSeconds;

            if (isInactive && isOlderThan1Min) {
               streamService.deleteStreamByRoomName(ingress.getRoomName());
            }
        }
        System.out.println("Ended cleaning inactive streams.");
    }

    @Scheduled(fixedDelay = FIXED_DELAY)
    public void updateStreamerStatus() throws IOException {
        IngressServiceClient client = clientService.getIngress();
        List<LivekitIngress.IngressInfo> ingresses = client.listIngress().execute().body();
        for (LivekitIngress.IngressInfo ingress : ingresses) {
            String roomName = ingress.getRoomName();
            Long userid=streamService.getUserIdByRoomName(roomName);
            boolean Inactive = ingress.getState().getStatus() == LivekitIngress.IngressState.Status.ENDPOINT_PUBLISHING;
            UserAndStreamConnectorService.updateUserStatus(userid, Inactive);
        }
    }

}
