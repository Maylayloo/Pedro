package com.example.backend.user.mapper;

import com.example.backend.user.dto.RegisterDTO;
import com.example.backend.user.model.MyUser;

public class RegisterMapper {

    public static MyUser toEntity(RegisterDTO dto) {
        MyUser user = new MyUser();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getUsername());
        user.setPedroCoin(0);
        return user;
    }
}
