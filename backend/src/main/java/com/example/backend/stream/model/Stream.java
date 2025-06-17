package com.example.backend.stream.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    String descryption;
    String roomName;
    String category;
    Long userId;
    public Stream(){}

    public Stream(String title, String descryption, String roomName, Long userId,String category) {
        this.title = title;
        this.descryption = descryption;
        this.roomName = roomName;
        this.userId = userId;
        this.category=category;
    }
}
