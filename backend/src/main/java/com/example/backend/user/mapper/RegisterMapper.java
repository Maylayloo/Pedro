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
        user.setDescription("");
        user.setProfilePictureUrl("http://www.gravatar.com/avatar/?d=mp");
        return user;
    }
}
