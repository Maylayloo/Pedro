package com.example.backend.user.controller;

import com.example.backend.user.auth.AuthenticatedUserService;
import com.example.backend.user.dto.LoginRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@RestController
@SessionAttributes("user")
public class LoginController {

    @Autowired
    private AuthenticatedUserService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest, HttpServletRequest request) {
        try {
            authenticationService.executeLoginRequest(loginRequest,request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
    }

}
