package com.simsoft.shapes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket konfigürasyon sınıfı
 * STOMP protokolü üzerinden WebSocket bağlantılarını yapılandırır
 */
@Configuration // Spring konfigürasyon sınıfı olarak işaretler
@EnableWebSocketMessageBroker // WebSocket mesaj broker'ını etkinleştirir
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Mesaj broker'ını yapılandırır
     * İstemciler arası mesaj iletişimini ayarlar
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // "/topic" prefix'i ile başlayan kanallarda basit broker etkinleştir
        // İstemciler /topic/shapes kanalına abone olabilir
        config.enableSimpleBroker("/topic");
        
        // İstemciden gelen mesajlar "/app" prefix'i ile başlamalı
        // Örnek: istemci /app/connect'e mesaj gönderebilir
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * STOMP endpoint'lerini kaydet
     * İstemcilerin bağlanabileceği WebSocket adreslerini tanımlar
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/shapes") // WebSocket endpoint adresi
                // Frontend geliştirme sunucularından bağlantıya izin ver
                .setAllowedOrigins("http://localhost:3000", "http://localhost:5173")
                // SockJS fallback desteği (WebSocket desteklemeyen tarayıcılar için)
                .withSockJS();
    }
}
