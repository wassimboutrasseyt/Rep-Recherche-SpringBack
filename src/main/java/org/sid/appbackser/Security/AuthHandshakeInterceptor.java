package org.sid.appbackser.Security;

import org.sid.appbackser.services.implementations.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuthHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthHandshakeInterceptor.class);
    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;



@Override
public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                Map<String, Object> attributes) throws Exception {
    logger.info("Request URI: " + request.getURI());
    
    // Retrieve token from query parameters
    String token = request.getURI().getQuery().split("=")[1]; // Assuming the token is passed as token=<token>

    if (token == null || token.isEmpty()) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED); // Reject connection if no token is found
        return false;
    }

    // Validate token and extract email
    String email = jwtService.extractEmail(token);
    if (email != null && jwtService.validateToken(token, userDetailsService.loadUserByUsername(email))) {
        attributes.put("email", email);  // Store email in session attributes
        return true;  // Allow WebSocket connection
    }

    response.setStatusCode(HttpStatus.UNAUTHORIZED);  // Invalid token
    return false;  // Reject connection
}



@Override
public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
        Exception exception) {
    // Log or handle actions after the handshake if needed
    if (exception != null) {
        System.out.println("Handshake failed: " + exception.getMessage());
    } else {
        System.out.println("Handshake succeeded!");
    }
}

}
