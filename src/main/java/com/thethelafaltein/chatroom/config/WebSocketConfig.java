package com.thethelafaltein.chatroom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import com.thethelafaltein.security.WebSocketAuthenticator;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketAuthenticator webSocketAuthenticator;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket").setAllowedOriginPatterns("*").withSockJS();      
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(10).maxPoolSize(100).queueCapacity(500).keepAliveSeconds(60);
        registration.interceptors(webSocketAuthenticator);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(10).maxPoolSize(100).queueCapacity(500).keepAliveSeconds(60);
        registration.interceptors(webSocketAuthenticator);
    }
  
}
