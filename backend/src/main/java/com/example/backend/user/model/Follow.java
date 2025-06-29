package com.example.backend.user.model;

import jakarta.persistence.*;

@Entity
public class Follow {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private MyUser follower;

    @ManyToOne
    private MyUser followed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUser getFollower() {
        return follower;
    }

    public void setFollower(MyUser follower) {
        this.follower = follower;
    }

    public MyUser getFollowed() {
        return followed;
    }

    public void setFollowed(MyUser followed) {
        this.followed = followed;
    }
}

