package com.example.backend.stream.repo;

import com.example.backend.stream.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamRepo extends JpaRepository<Long, Stream> {
}
