package Message;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String sender;
    private String receiver;
    private String text;
    private Action action;
    private ArrayList<String> OnlineUsers;
    
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public ArrayList<String> getOnlineUsers() {
        return OnlineUsers;
    }

    public void setOnlineUsers(ArrayList<String> OnlineUsers) {
        this.OnlineUsers = OnlineUsers;
    }

    public enum Action {
        CONNECT, DISCONNECT, SEND, SEND_ONE, USERS_ONLINE
    }
}
