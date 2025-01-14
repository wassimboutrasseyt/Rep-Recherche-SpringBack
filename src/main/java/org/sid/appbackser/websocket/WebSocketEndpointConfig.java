package org.sid.appbackser.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketEndpointConfig implements WebSocketConfigurer {

    @Bean
    public GroupChatWebSocketHandler groupChatWebSocketHandler() {
        return new GroupChatWebSocketHandler();
    }

    @Bean
    public PrivateChatWebSocketHandler privateChatWebSocketHandler() {
        return new PrivateChatWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // registry.addHandler(groupChatWebSocketHandler(), "/ws/chat/{projectId}/{groupId}")
        //     .addInterceptors(new HttpSessionHandshakeInterceptor())
        //     .setAllowedOrigins("*"); 

        // registry.addHandler(privateChatWebSocketHandler(), "/ws/chat/private/{projectId}/{recieverId}")
        //     .addInterceptors(new HttpSessionHandshakeInterceptor())
        //     .setAllowedOrigins("*");
    }
}
