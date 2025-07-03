package com.example.backend.user.controller;

import com.example.backend.user.dto.SearchRequestDTO;
import com.example.backend.user.dto.SearchResponseDTO;
import com.example.backend.user.dto.UserProfileDTO;
import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.MyUserRepository;
import com.example.backend.user.service.UserDataService;
import com.example.backend.user.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserDataService userDataService;
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private UserSearchService userSearchService;

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

    @GetMapping("/id/{userId}")
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

    @PostMapping("/search")
    public ResponseEntity<List<SearchResponseDTO>> searchUsers(@RequestBody SearchRequestDTO request) {
        List<SearchResponseDTO> users = userSearchService.searchUsers(request.getQuery());
        //System.out.println("users:" + users);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/nick/{nickname}")
    public ResponseEntity<?> getUserByNickname(@PathVariable String nickname) {
        Optional<MyUser> userOptional = userDataService.getUserByNickname(nickname);

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

    @PostMapping("/refill")
    public ResponseEntity<String> topUpPedroCoins() {
        Optional<MyUser> userOpt = userDataService.getLoggedInUser();
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        MyUser user = userOpt.get();
        userDataService.addPedroCoins(user.getId(), 500);

        return ResponseEntity.ok("500 pedro coins added to your account: " + user.getNickname());
    }

    @GetMapping("/me/coins")
    public ResponseEntity<?> getMyCoins() {
        Optional<MyUser> currentUser = userDataService.getLoggedInUser();

        if (currentUser.isPresent()) {
            MyUser user = currentUser.get();
            return ResponseEntity.ok(user.getPedroCoin());
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated");
        }
    }
}
