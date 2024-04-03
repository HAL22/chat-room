package com.thethelafaltein.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import jakarta.servlet.http.HttpSession;
import org.springframework.messaging.Message;


@Component
public class WebSocketAuthenticator implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (headerAccessor.getSubscriptionId() == null) {
            String authorization = headerAccessor.getNativeHeader("Authorization").get(0);
            if (headerAccessor.getCommand() != null && "basic".equals(headerAccessor.getCommand().toString().toLowerCase())) {
                byte[] decodeBytes = Base64.decodeBase64(authorization.substring(6));
                String decodedText = new String(decodeBytes);
                String[] split = decodedText.split(":", 2);
                if (split.length >= 2) {
                    String username = split[0].trim();
                    String password = split[1].trim();
                    // User user = userRepository.findByUsername(username);
                    if (true) {
                       // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(null, "password", null);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        throw new BadCredentialsException("Invalid username or password.");
                    }
                }
            }

            
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception exception) {
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception exception) {
    }
}
