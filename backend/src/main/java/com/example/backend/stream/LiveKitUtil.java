package com.example.backend.stream;



import io.livekit.server.AccessToken;
import io.livekit.server.Room;
import io.livekit.server.RoomJoin;

import java.util.HashMap;
import java.util.Map;

public class LiveKitUtil {


    public static Map<String,String> createToken() {
        AccessToken token = new AccessToken("apiKey", "secret");
        token.setName("participant-name");
        token.setIdentity("participant-identity");
        token.setMetadata("metadata");
        token.addGrants(new RoomJoin(true), new Room("room-name"));

        return makeJsonLikeResponse(token.toJwt());
    }
    public static Map<String,String> makeJsonLikeResponse(String token){
        Map<String,String> result=new HashMap<>();
        result.put("token",token);
        return result;
    }


}
