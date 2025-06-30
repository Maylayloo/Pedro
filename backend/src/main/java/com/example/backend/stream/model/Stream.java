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
    String description;
    String roomName;
    String category;
    Long creationTime;
    String ingressId;
    Long userId;
    public Stream(){}

    public Stream(String title, String description, String roomName, Long userId,String category,String ingressId) {
        this.title = title;
        this.description = description;
        this.roomName = roomName;
        this.userId = userId;
        this.category=category;
        this.creationTime=System.currentTimeMillis();
        this.ingressId=ingressId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public String getIngressId() {
        return ingressId;
    }

    public void setIngressId(String ingressId) {
        this.ingressId = ingressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
