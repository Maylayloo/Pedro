package com.example.backend.user.dto;

import com.example.backend.user.model.MyUser;

public class UserProfileDTO {
    private Long id;
    private String email;
    private String nickname;
    private String description;
    private int pedroCoin;
    private String profilePictureUrl;

    public UserProfileDTO(MyUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.description = user.getDescription();
        this.pedroCoin = user.getPedroCoin();
        this.profilePictureUrl = user.getProfilePictureUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPedroCoin() {
        return pedroCoin;
    }

    public void setPedroCoin(int pedroCoin) {
        this.pedroCoin = pedroCoin;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}