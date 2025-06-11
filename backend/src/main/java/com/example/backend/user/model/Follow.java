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
}

