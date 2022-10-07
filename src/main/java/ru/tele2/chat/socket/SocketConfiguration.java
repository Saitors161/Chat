package ru.tele2.chat.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.tele2.chat.service.MessageService;

@Configuration
@EnableWebSocket
public class SocketConfiguration implements WebSocketConfigurer {
    private final MessageService messageService;
    private final TextWebSocketHandler textWebSocketHandler;

    public SocketConfiguration(MessageService messageService, org.springframework.web.socket.handler.TextWebSocketHandler textWebSocketHandler) {
        this.messageService = messageService;
        this.textWebSocketHandler = textWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(textWebSocketHandler,"/websocket").setAllowedOrigins("*");
    }
}
