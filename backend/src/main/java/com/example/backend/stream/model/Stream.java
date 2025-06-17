package com.example.backend.stream.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    Long userId;
    Stream(){}

    public Stream(String title, String descryption, String roomName, Long userId) {
        this.title = title;
        this.descryption = descryption;
        this.roomName = roomName;
        this.userId = userId;
    }
}
