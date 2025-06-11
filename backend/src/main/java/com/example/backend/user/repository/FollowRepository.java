package com.example.backend.user.repository;

import com.example.backend.user.model.Follow;
import com.example.backend.user.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowed(MyUser Followed);
    List<Follow> findByFollower(MyUser follower);

    boolean existsByFollowerAndFollowed(MyUser follower, MyUser followed);
    void deleteByFollowerAndFollowed(MyUser follower, MyUser followed);

    long countByFollowed(MyUser followed);
    long countByFollower(MyUser follower);
}
