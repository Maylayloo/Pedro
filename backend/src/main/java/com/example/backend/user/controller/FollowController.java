package com.example.backend.user.controller;

import com.example.backend.user.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

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
}
