package lab17.chatappwebsocket.Controller;

import lab17.chatappwebsocket.DTO.CreateChatMessageBody;
import lab17.chatappwebsocket.Model.ChatMessage;
import lab17.chatappwebsocket.Model.MessageType;
import lab17.chatappwebsocket.Model.User;
import lab17.chatappwebsocket.Model.UserNumber;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(CreateChatMessageBody createChatMessageBody) {
        String username = createChatMessageBody.getSender();
        String message = createChatMessageBody.getMessage();
        MessageType messageType = createChatMessageBody.getType();
            return ChatMessage.buildChatMessage(message,username,messageType);
    }

    @MessageMapping("/chat/addUser") //received
    @SendToUser("/queue/connected") //unicast
    public User addUser(CreateChatMessageBody createChatMessageBody
            ,SimpMessageHeaderAccessor headerAccessor) {
        String username = createChatMessageBody.getSender();
        String message = createChatMessageBody.getMessage();
        MessageType messageType = createChatMessageBody.getType();
        headerAccessor.getSessionAttributes().put("username" , username);
        UserNumber userNumber = new UserNumber();
        messagingTemplate.convertAndSend("/topic/messages"
                , ChatMessage.buildChatMessage(message,username,messageType));
        messagingTemplate.convertAndSend("/topic/chatNumber", userNumber);

        return new User(username);
    }
}
