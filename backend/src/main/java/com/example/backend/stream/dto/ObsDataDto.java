package com.example.backend.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObsDataDto {
    String url;
    String streamKey;

    public ObsDataDto(String url, String streamKey) {
        this.url = url;
        this.streamKey = streamKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }
}
