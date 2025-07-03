package com.example.backend.user.service;

import com.example.backend.user.dto.FollowedUserDTO;
import com.example.backend.user.model.Follow;
import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.FollowRepository;
import com.example.backend.user.repository.MyUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private UserDataService userDataService;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private MyUserRepository myUserRepository;

    @Transactional
    public void followUser(Long followedId){
        MyUser currentUser = userDataService.getLoggedInUser()
                .orElseThrow(() -> new RuntimeException("User must be lgged in to follow"));

        MyUser followedUser = myUserRepository.findById(followedId)
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        if (currentUser.getId().equals(followedUser.getId())) {
            throw new RuntimeException("You cannot follow yourself");
        }
        if (followRepository.existsByFollowerAndFollowed(currentUser, followedUser)) {
            throw new RuntimeException("You are already following this user");
        }

        Follow follow = new Follow();
        follow.setFollower(currentUser);
        follow.setFollowed(followedUser);

        followRepository.save(follow);
    }

    @Transactional
    public void unfollowUser(Long followedId){
        MyUser currentUser = userDataService.getLoggedInUser()
                .orElseThrow(() -> new RuntimeException("User must be logged in to unfollow"));

        MyUser followedUser = myUserRepository.findById(followedId)
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        if (!followRepository.existsByFollowerAndFollowed(currentUser, followedUser)) {
            throw new RuntimeException("You are not following this user");
        }

        followRepository.deleteByFollowerAndFollowed(currentUser, followedUser);
    }

    public long getFollowersCount(Long userId) {
        MyUser user = myUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return followRepository.countByFollowed(user);
    }

    public long getFollowingCount(Long userId) {
        MyUser user = myUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return followRepository.countByFollower(user);
    }

    public Boolean checkIfStreamerIsFollowedByMeByStreamerNickName(String nickName) {
        Optional<MyUser> currentUser = userDataService.getLoggedInUser();
        List<FollowedUserDTO> followed=followRepository.findFollowedUsersWithStatus(currentUser.get().getId());
        return followed.stream()
                .anyMatch(f -> f.nickname().equalsIgnoreCase(nickName));
    }
}
