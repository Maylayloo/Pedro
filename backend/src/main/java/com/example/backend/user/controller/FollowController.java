package com.example.backend.user.controller;

import com.example.backend.user.dto.FollowedUserDTO;
import com.example.backend.user.model.Follow;
import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.FollowRepository;
import com.example.backend.user.service.FollowService;
import com.example.backend.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private FollowRepository followRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<?> followUser(@PathVariable Long userId) {
        try {
            followService.followUser(userId);
            return ResponseEntity.ok("Followed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> unfollowUser(@PathVariable Long userId) {
        try {
            followService.unfollowUser(userId);
            return ResponseEntity.ok("Unfollowed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowersCount(@PathVariable Long userId) {
        try {
            long count = followService.getFollowersCount(userId);
            return ResponseEntity.ok(Map.of("userId", userId, "followersCount", count));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/following/count")
    public ResponseEntity<?> getFollowingCount(@PathVariable Long userId) {
        try {
            long count = followService.getFollowingCount(userId);
            return ResponseEntity.ok(Map.of("userId", userId, "followingCount", count));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/myFollow")
    public ResponseEntity<List<FollowedUserDTO>> getFollowedUsers() {
        return userDataService.getLoggedInUser()
                .map(user -> {
                    List<FollowedUserDTO> followed = followRepository.findFollowedUsersWithStatus(user.getId());
                    return ResponseEntity.ok(followed);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @GetMapping("/myFollow/{nickName}")
    public ResponseEntity<Map<String,Boolean>> checkIfIFollowThisStreamer(@PathVariable String nickName){
        return new ResponseEntity<>(followService.checkIfStreamerIsFollowedByMeByStreamerNickName(nickName)
                , HttpStatus.OK);

    }
}
