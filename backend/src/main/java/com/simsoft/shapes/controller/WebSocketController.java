package com.simsoft.shapes.controller;

import com.simsoft.shapes.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * WebSocket kontrolcüsü
 * İstemci bağlantılarını yönetir ve panel bilgilerini gönderir
 */
@Controller // Spring MVC kontrolcüsü olarak işaretler
public class WebSocketController {
    
    // ShapeService'i otomatik olarak enjekte et
    @Autowired
    private ShapeService shapeService;
    
    /**
     * WebSocket bağlantı olayını dinler
     * Yeni bir istemci bağlandığında otomatik olarak çağrılır
     * 
     * @param event Bağlantı olayı bilgileri
     */
    @EventListener // Spring olay dinleyicisi
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        // Yeni bağlanan istemciye panel bilgilerini gönder
        // Panel boyutları (1000x800) ilk mesaj olarak iletilir
        shapeService.sendPanelInfo();
    }
    
    /**
     * İstemci açık olarak bağlantı isteği gönderdiğinde çağrılır
     * /app/connect adresine gelen mesajları yakalar
     */
    @MessageMapping("/connect") // WebSocket mesaj yönlendirmesi
    @SendTo("/topic/shapes")    // Yanıtı /topic/shapes kanalına gönder
    public void handleConnect() {
        // İstemci manuel bağlantı talebinde panel bilgilerini gönder
        shapeService.sendPanelInfo();
    }
}
