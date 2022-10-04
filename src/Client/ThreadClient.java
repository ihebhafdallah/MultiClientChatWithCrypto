package Client;

import Crypto.Crypto;
import Message.Message;
import Message.Message.Action;
import Server.ThreadServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class ThreadClient extends Thread {
    
    private Socket socket;
    private JTextArea textArea;
    private String remetente;
    private int Key = 1;
    boolean goOut = false;
    
    public ThreadClient(String r, Socket s, JTextArea textArea) {
        this.remetente = r;
        this.socket = s;
        this.textArea = textArea;
    }
    
    @Override
    public void run() {
        try {
            while (!goOut) {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) input.readObject();
                Action action = message.getAction();

                switch (action) {
                    case CONNECT:
                        toConnect(message);
                        break;
                    case DISCONNECT:
                        disconnect(message);
                        break;
                    case SEND:
                        receiveMessage(message);
                        break;
                    case SEND_ONE:
                        receiveMessage(message);
                        break;
                    case USERS_ONLINE:
                        updateUsers(message);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void toConnect(Message message) {
        this.textArea.setText(textArea.getText() + message.getSender() + " >> " + message.getText() + "\n");
    }
    
    public void disconnect(Message message) throws IOException {
        this.textArea.setText(textArea.getText() + message.getSender() + " >> " + message.getText() + "\n");

        if (message.getSender().equals(this.remetente)) {
            this.socket.close();
            this.goOut = true;
        }
    }
    
    public void receiveMessage(Message message) throws IOException {
        Crypto crypto = new Crypto();
        this.textArea.setText(textArea.getText() + message.getSender() + " >> " + crypto.decrypt(Key, message.getText()) + "\n");
    }
    
    public void updateUsers(Message message) {
        ArrayList<String> usuariosOnline = message.getOnlineUsers();
    }
}
