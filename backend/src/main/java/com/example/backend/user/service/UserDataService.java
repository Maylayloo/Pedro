package com.example.backend.user.service;

import com.example.backend.user.dto.RegisterDTO;
import com.example.backend.user.mapper.RegisterMapper;
import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataService {
    @Autowired
    public MyUserRepository MyUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public Optional<MyUser> loadUserByEmail(String email) {
        return MyUserRepository.findByEmail(email);
    }


    public Optional<MyUser> getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String email = userDetails.getUsername();

        return loadUserByEmail(email);
    }

    public void registerUser(RegisterDTO registerDTO) {
        if (MyUserRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        if (MyUserRepository.findByNickname(registerDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User with this nickname already exists");
        }

        MyUser user = RegisterMapper.toEntity(registerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        MyUserRepository.save(user);
    }

    public Optional<MyUser> getUserById(Long id) {
        return MyUserRepository.findById(id);
    }
}
