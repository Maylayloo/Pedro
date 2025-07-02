package com.example.backend.stream.dto;

public class StreamDto {
    String title;
    String description;
    String roomName;
    String category;
    Long creationTime;
    String ingressId;
    Long userId;
    public StreamDto(){}
    public StreamDto(String title, String description,
                     String roomName, String category, Long creationTime, String ingressId, Long userId) {
        this.title = title;
        this.description = description;
        this.roomName = roomName;
        this.category = category;
        this.creationTime = creationTime;
        this.ingressId = ingressId;
        this.userId = userId;
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
