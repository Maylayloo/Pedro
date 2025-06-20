package com.example.backend.user.controller;

import com.example.backend.user.dto.UserProfileDTO;
import com.example.backend.user.model.MyUser;
import com.example.backend.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserDataService userDataService;

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
}
