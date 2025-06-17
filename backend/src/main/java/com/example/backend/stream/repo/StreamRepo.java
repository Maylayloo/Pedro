package com.example.backend.stream.repo;

import com.example.backend.stream.dto.StreamDto;
import com.example.backend.stream.model.Stream;
import jakarta.transaction.Transactional;
import org.intellij.lang.annotations.JdkConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.Transient;

@Repository
public interface StreamRepo extends JpaRepository<Stream,Long> {
    @Transactional
    void deleteByRoomName(String roomName);

    StreamDto findByRoomName(String roomName);
}
