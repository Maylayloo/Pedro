package com.example.backend.stream.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    String descryption;
    String roomName;
    String category;
    Long creationTime;
    String ingressId;
    Long userId;
    public Stream(){}

    public Stream(String title, String descryption, String roomName, Long userId,String category,String ingressId) {
        this.title = title;
        this.descryption = descryption;
        this.roomName = roomName;
        this.userId = userId;
        this.category=category;
        this.creationTime=System.currentTimeMillis();
        this.ingressId=ingressId;
    }
}
