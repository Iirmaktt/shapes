package com.simsoft.shapes.controller;

import com.simsoft.shapes.service.ShapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Controller
public class WebSocketController {
    
    @Autowired
    private ShapeService shapeService;
    
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        // Send panel info to new client
        shapeService.sendPanelInfo();
    }
    
    @MessageMapping("/connect")
    @SendTo("/topic/shapes")
    public void handleConnect() {
        // Send panel info when client explicitly connects
        shapeService.sendPanelInfo();
    }
}
