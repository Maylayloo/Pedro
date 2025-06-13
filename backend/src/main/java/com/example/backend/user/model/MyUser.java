package com.example.backend.user.model;

import jakarta.persistence.*;

@Entity
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;
    private int pedroCoin;
    private String description;
    private boolean status; // online or offline
    //potentialy followers, better to use external model for this

    //@OneToOne(mappedBy = "user")
    //private Stream stream;

    public MyUser() {}


    public MyUser(String email, String nickname, String password, int pedroCoin, String description, boolean status) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.pedroCoin = pedroCoin;
        this.description = description;
        this.status = status;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPedroCoin() {
        return pedroCoin;
    }

    public void setPedroCoin(int pedroCoin) {
        this.pedroCoin = pedroCoin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }
}
