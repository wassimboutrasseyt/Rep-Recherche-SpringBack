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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthHandshakeInterceptor.class);

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String token = request.getHeaders().getFirst("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {

             System.out.println("header ." + request.getHeaders());
             System.out.println("Invalid or missing token." + token);
            return false;
        }
    
        token = token.substring(7); // Remove "Bearer " prefix
        String email = jwtService.extractEmail(token);
        System.out.println("Extracted Email: " + email);
    
        if (email == null) {
            System.out.println("Invalid token: Unable to extract email.");
            return false;
        }
    
        boolean isValid = validateToken(token, email);
        System.out.println("Token Valid: " + isValid);
        if (!isValid) {
            System.out.println("Token validation failed.");
            return false;
        }
    
        attributes.put("email", email);
        return true;
    }
    

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        if (exception != null) {
            logger.error("Error occurred during WebSocket handshake: {}", exception.getMessage());
        } else {
            logger.info("WebSocket handshake completed successfully.");
        }
    }

    private boolean validateToken(String token, String email) {
        try {
            AccountDetailsService accountDetailsService = context.getBean(AccountDetailsService.class);
            UserDetails userDetails = accountDetailsService.loadUserByUsername(email);
            boolean isValid = jwtService.validateToken(token, userDetails);
            logger.debug("Token validation status for email {}: {}", email, isValid);
            return isValid;
        } catch (Exception e) {
            logger.error("Error during token validation: {}", e.getMessage());
            return false;
        }
    }
}
