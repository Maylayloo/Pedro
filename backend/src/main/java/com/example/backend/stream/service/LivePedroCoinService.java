package com.example.backend.stream.service;

import com.example.backend.stream.message.UserAndStreamConnectorService;
import com.example.backend.stream.model.LivePedroCoin;
import com.example.backend.stream.model.Stream;
import com.example.backend.stream.repo.LivePedrocoinRepo;
import com.example.backend.stream.repo.StreamRepo;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LivePedroCoinService {
    private LivePedrocoinRepo livePedrocoinRepo;
    private StreamRepo streamrepo;

    public LivePedroCoinService(LivePedrocoinRepo livePedrocoinRepo, StreamRepo streamrepo) {
        this.livePedrocoinRepo = livePedrocoinRepo;
        this.streamrepo = streamrepo;
    }

    public void createLivePedroCoin(Stream stream){
        int beginingStreamPedrocoinValue=0;
        LivePedroCoin coin=new LivePedroCoin();
        coin.setStream(stream);
        coin.setCoinsValue(beginingStreamPedrocoinValue);
        livePedrocoinRepo.save(coin);
    }

    public void donateStreamerWithcoinAmountByStreamId(Long streamid,int coinvalue){
        LivePedroCoin coin=livePedrocoinRepo.findByStreamId(streamid);
        coin.donate(coinvalue);
        livePedrocoinRepo.save(coin);
        Long userid=streamrepo.findUserIdByStreamId(streamid);
        UserAndStreamConnectorService.updateUserPedroCoinValueByStreamid(userid,coinvalue);
    }

    public int getPedrocoinValueByStreamId(Long streamid){
        LivePedroCoin coin=livePedrocoinRepo.findByStreamId(streamid);
        return coin.getCoinsValue();
    }

    public void removeByStreamId(Long streamid){
        livePedrocoinRepo.removeByStream_Id(streamid);
    }
    public void removeByRoomName(String roomName) {
        Stream s=streamrepo.findByRoomName(roomName);
        removeByStreamId(s.getId());
    }
}
