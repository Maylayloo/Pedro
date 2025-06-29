package com.example.backend.stream.dto;

import lombok.Getter;

@Getter
public class ParticipantDto {
    String participantName;
    String identity;

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
