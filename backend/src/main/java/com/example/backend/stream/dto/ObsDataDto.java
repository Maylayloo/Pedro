package com.example.backend.stream.dto;

public class ObsDataDto {
    String url;
    String streamKey;

    public String getUrl() {
        return url;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public ObsDataDto(String url, String streamKey) {
        this.url = url;
        this.streamKey = streamKey;
    }
}
