package org.sid.appbackser.Security;

import org.sid.appbackser.services.implementations.AccountDetailsService;
import org.sid.appbackser.services.implementations.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JWTService jwtService;

    @Autowired
	private ApplicationContext context;
	
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // Extract the token from the query parameters or headers
        String token = request.getHeaders().getFirst("Authorization");
        String email;
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
			email=jwtService.extractEmail(token);
            // Validate the token (e.g., using JWT library)
            boolean isValid = validateToken(token, email);
            if (isValid) {
                attributes.put("token", token); // Store it in attributes for later use
                return true;
            }
        }
        return false; // Reject the handshake if the token is invalid
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                            WebSocketHandler wsHandler, Exception exception) {
        // Post-handshake logic (if needed)
    }

    private boolean validateToken(String token, String email) {
        UserDetails userDetails= context.getBean(AccountDetailsService.class).loadUserByUsername(email);
        return jwtService.validateToken(token, userDetails);
    }
}