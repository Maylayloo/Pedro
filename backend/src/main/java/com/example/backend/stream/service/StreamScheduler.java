package com.example.backend.stream.service;

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

    public StreamScheduler(ClientService clientService, StreamService streamService) {
        this.clientService = clientService;
        this.streamService = streamService;
    }


    @Scheduled(fixedDelay = 60 * 1000) // every 5 minutes
    public void cleanUpOldIngresses() throws IOException {
        IngressServiceClient client = clientService.getIngress();
        List<LivekitIngress.IngressInfo> ingresses = client.listIngress().execute().body();

        long nowEpochSeconds = System.currentTimeMillis() / 1000;
        long fiveMinutesInSeconds = 60;

        for (LivekitIngress.IngressInfo ingress : ingresses) {
            boolean isInactive = ingress.getState().getStatus() == LivekitIngress.IngressState.Status.ENDPOINT_INACTIVE;
            Optional<String> data= Optional.of(ingress.getIngressId());
            System.out.println(isInactive);

            long createdAt = streamService.getCreationTimeByIngress(ingress.getIngressId());// Assuming it's in epoch seconds


            boolean isOlderThan5Min = nowEpochSeconds - createdAt/1000 > fiveMinutesInSeconds;
            System.out.println(nowEpochSeconds - createdAt/1000 > fiveMinutesInSeconds);
            System.out.println(isOlderThan5Min);
            if (isInactive && isOlderThan5Min) {
               streamService.deleteStreamByRoomName(ingress.getRoomName());
            }
        }
        System.out.println("Ended cleaning inactive streams.");
    }

}
