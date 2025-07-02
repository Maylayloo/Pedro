package com.example.backend.user.repository;

import com.example.backend.user.dto.FollowedUserDTO;
import com.example.backend.user.model.Follow;
import com.example.backend.user.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowed(MyUser Followed);
    List<Follow> findByFollower(MyUser follower);

    boolean existsByFollowerAndFollowed(MyUser follower, MyUser followed);
    void deleteByFollowerAndFollowed(MyUser follower, MyUser followed);

    long countByFollowed(MyUser followed);
    long countByFollower(MyUser follower);

    @Query("""
    SELECT new com.example.backend.user.dto.FollowedUserDTO(
        f.followed.userId,
        f.followed.nickname,
        f.followed.status
    )
    FROM Follow f
    WHERE f.follower.userId = :followerId
""")
    List<FollowedUserDTO> findFollowedUsersWithStatus(@Param("followerId") Long followerId);
}
