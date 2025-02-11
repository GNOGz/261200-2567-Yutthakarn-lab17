package lab17.chatappwebsocket.Config;

import lab17.chatappwebsocket.Model.ChatMessage;
import lab17.chatappwebsocket.Model.MessageType;
import lab17.chatappwebsocket.Model.UserNumber;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            ChatMessage chatMessage = ChatMessage.buildChatMessage(username+ " has left the chat.",username, MessageType.LEAVE);
            messagingTemplate.convertAndSend("/topic/messages", chatMessage);
        }
        UserNumber userNumber = UserNumber.reduceUserNumber();
        messagingTemplate.convertAndSend("/topic/chatNumber", userNumber);

    }
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        UserNumber userNumber = UserNumber.addUserNumber();
        messagingTemplate.convertAndSend("/topic/chatNumber",userNumber);
    }
}
