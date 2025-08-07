package com.simsoft.shapes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration // Spring konfigürasyon sınıfı olarak işaretler
@EnableWebSocketMessageBroker // WebSocket mesaj broker'ını etkinleştirir
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
// socket açmak için messagebroker kullandık
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
       
        config.enableSimpleBroker("/topic");
        
        config.setApplicationDestinationPrefixes("/app");
    }
//socket kütüphanesi stomp react kısmında
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/shapes") //socket anaurl i frontedne bağşlanıyp
               
                .setAllowedOrigins("http://localhost:3000", "http://localhost:5173")
                
                .withSockJS();
    }
}
