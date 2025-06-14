package com.example.backend.user.service;

import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {
    @Autowired
    public MyUserRepository MyUserRepository;

    public MyUser getUserByEmail(String email){
        return MyUserRepository.findByEmail(email).isPresent() ? MyUserRepository.findByEmail(email).get() : null;
    }
}
