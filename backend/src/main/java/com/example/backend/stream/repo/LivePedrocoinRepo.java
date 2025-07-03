package com.example.backend.stream.repo;

import com.example.backend.stream.model.LivePedroCoin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface LivePedrocoinRepo extends JpaRepository<LivePedroCoin,Long> {
    LivePedroCoin findByStreamId(Long streamid);
    @Modifying
    void removeByStream_Id(Long streamId);
}
