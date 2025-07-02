package com.example.backend.jwt;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // Sprawdź czy jest sessionAttributes i czy jest tam auth
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (sessionAttributes != null) {
            Authentication auth = (Authentication) sessionAttributes.get("auth");
            System.out.println("Channel Session attributes: " + sessionAttributes);
            System.out.println("Channel Authentication: " + auth);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("Channel SecurityContextHolder set with auth: " + auth);
                System.out.println("Channel SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());
                accessor.setUser(auth);
            }
        }
        return message;
    }
}
