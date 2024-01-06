package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * In this class the WebSockets for the chat feature are implemented.
 * For this following tutorials where followed:
 * https://spring.io/guides/gs/messaging-stomp-websocket/
 * https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/websocket.html
 * https://www.baeldung.com/websockets-spring
 * https://www.youtube.com/watch?v=7T-HnTE6v64&t=2686s
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Define the message broker path
        registry.enableSimpleBroker("/chat");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Enable SockJS fallback for browsers that don't support WebSocket yet
        registry.addEndpoint("/chat").withSockJS();
    }
}
