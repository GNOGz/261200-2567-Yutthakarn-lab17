package lab17.chatappwebsocket.DTO;

import lab17.chatappwebsocket.Model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class CreateChatMessageBody {
    private String message;
    private String sender;
    private MessageType type;

}
