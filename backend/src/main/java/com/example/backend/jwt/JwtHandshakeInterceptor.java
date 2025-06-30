package com.example.backend.jwt;

import com.example.backend.user.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import com.example.backend.jwt.JwtUtil;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JwtHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        System.out.println("Starting WebSocket handshake with request: " + request.getURI());
        HttpServletRequest servletRequest = null;
        if (request instanceof ServletServerHttpRequest servletServerHttpRequest) {
            servletRequest = servletServerHttpRequest.getServletRequest();
        }
        String token = getTokenFromRequest(request);
        System.out.println("Token received: " + token);
        Long userId = null;
        try {
            userId = jwtUtil.extractUserId(token);
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token has expired: " + e.getMessage());
        }catch (JwtException e) {
            System.out.println("Invalid JWT token: " +  e.getMessage());
        }
        System.out.println("Extracted userId: " + userId);
        System.out.println("SecurityContextHolder before setting auth: " + SecurityContextHolder.getContext().getAuthentication());
        if (userId != null){
            UserDetails userDetails = userDetailsService.loadUserById(userId);
            if (token != null && jwtUtil.validateToken(token)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest));
                System.out.println("Setting authentication in SecurityContextHolder for userId: " + userId +" qith byczq token: " + authToken);
                attributes.put("auth", authToken);
                System.out.println("Attributes after setting auth: " + attributes);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("SecurityContextHolder after setting auth: " + SecurityContextHolder.getContext().getAuthentication());
            }
            else{
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        // Możesz tu wyciągnąć token np. z URI lub nagłówka
        List<String> tokenList = request.getHeaders().get("Authorization");
        if (tokenList != null && !tokenList.isEmpty()) {
            String bearer = tokenList.get(0);
            if (bearer.startsWith("Bearer ")) {
                return bearer.substring(7);
            }
        }
        // Lub z query params, jeśli to prostsze
        URI uri = request.getURI();
        String query = uri.getQuery(); // np. token=xxx
        if (query != null && query.startsWith("token=")) {
            return query.substring(6);
        }
        return null;
    }
}
