package com.example.backend.user.controller;

import com.example.backend.user.dto.UserProfileDTO;
import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.MyUserRepository;
import com.example.backend.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserDataService userDataService;
    @Autowired
    private MyUserRepository myUserRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {
        Optional<MyUser> currentUser = userDataService.getLoggedInUser();

        if (currentUser.isPresent()) {
            MyUser user = currentUser.get();
            UserProfileDTO dto = new UserProfileDTO(user);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody UserProfileDTO userProfileDTO) {
        Optional<MyUser> currentUser = userDataService.getLoggedInUser();

        if (currentUser.isPresent()) {
            MyUser user = currentUser.get();
            if (userProfileDTO.getEmail() != null && !userProfileDTO.getEmail().equals(user.getEmail())) {
                if (myUserRepository.findByEmail(userProfileDTO.getEmail()).isPresent()) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body("Email already in use");
                }
                user.setEmail(userProfileDTO.getEmail());
            }
            if(userProfileDTO.getUsername() != null && !userProfileDTO.getUsername().equals(user.getNickname())) {
                if (myUserRepository.findByNickname(userProfileDTO.getUsername()).isPresent()) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body("Nickname already in use");
                }
                user.setNickname(userProfileDTO.getUsername());
            }
            if (userProfileDTO.getDescription() != null) {
                user.setDescription(userProfileDTO.getDescription());
            }
            myUserRepository.save(user);
            return ResponseEntity.ok(new UserProfileDTO(user));
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        Optional<MyUser> userOptional = userDataService.getUserById(userId);

        if (userOptional.isPresent()) {
            MyUser user = userOptional.get();
            UserProfileDTO dto = new UserProfileDTO(user);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }


}
