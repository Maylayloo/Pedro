package com.example.backend.stream.repo;

import com.example.backend.stream.model.LivePedroCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivePedrocoinRepo extends JpaRepository<LivePedroCoin,Long> {
    LivePedroCoin findByStreamId(Long streamid);
}
