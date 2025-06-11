package com.example.backend.user.repository;

import com.example.backend.user.model.Follow;
import com.example.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowed(User Followed);
    List<Follow> findByFollower(User follower);

    boolean existsByFollowerAndFollowed(User follower, User followed);
    void deleteByFollowerAndFollowed(User follower, User followed);

    long countByFollowed(User followed);
    long countByFollower(User follower);
}
