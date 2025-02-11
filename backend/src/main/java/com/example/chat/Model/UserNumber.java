package lab17.chatappwebsocket.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.min;

@Getter
@Setter
@AllArgsConstructor
public class UserNumber {
    static int allUsersNumber = 0;
    private int userNumber;

    public static UserNumber addUserNumber() {
        return new UserNumber(++allUsersNumber);
    }
    public static UserNumber reduceUserNumber(){
        return new UserNumber(min(0,--allUsersNumber));
    }
    public UserNumber() {
        this.userNumber = allUsersNumber;
    }
}
