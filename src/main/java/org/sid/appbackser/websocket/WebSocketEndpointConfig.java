package org.sid.appbackser.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketEndpointConfig implements WebSocketConfigurer {

    @Autowired
    private GroupChatWebSocketHandler groupChatWebSocketHandler; 

    // public WebSocketEndpointConfig(GroupMessageWebSocketHandler messageWebSocketHandler) {
    //     this.messageWebSocketHandler = messageWebSocketHandler;
    // }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(groupChatWebSocketHandler, "/ws/chat/{chatGroupId}")
            .addInterceptors(new HttpSessionHandshakeInterceptor())
            .setAllowedOrigins("*"); 
        
            registry.addHandler(groupChatWebSocketHandler, "/ws/private/{receiverId}")
            .addInterceptors(new HttpSessionHandshakeInterceptor())
            .setAllowedOrigins("*");
            
    }
}
