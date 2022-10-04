package Server;

import Message.Message;
import Message.Message.Action;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServer extends Thread {
    
    private static Map <String, Socket> clientesMap = new HashMap<>();
    private Socket socket;

    public ThreadServer(Socket s) {
        this.socket = s;
    }
    
    @Override
    public void run() {
        boolean goOut = false;
        try {
            while (!goOut) {
                //Receiving message from the Client
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) input.readObject();
                Action action = message.getAction();
                
                    
                
                switch (action) {
                    case CONNECT:
                        System.out.println("The User: "+ message.getSender() + " Join The Chat !!! ");
                        toConnect(message);
                        sendMessageAll(message);
                        sendUsersOnline();
                        break;
                    case DISCONNECT:
                        System.out.println("The User: "+ message.getSender() + " Logged Out Of Chat !!! ");
                        disconnect(message);
                        sendMessageAll(message);
                        sendUsersOnline();
                        goOut = true;
                        break;
                    case SEND:
                        System.out.println("Form : "+ message.getSender() + " >> Message: " + message.getText());
                        sendMessageAll(message);
                        break;
                    case SEND_ONE:
                        sendReservedMessage(message);
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
        clientesMap.put(message.getSender(), socket);
    }
    
    public void disconnect(Message message) throws IOException {
        clientesMap.remove(message.getSender());
    }
    
    public void sendMessageAll(Message message) throws IOException {
        for (Map.Entry<String, Socket> cliente : clientesMap.entrySet()) {
            ObjectOutputStream output = new ObjectOutputStream(cliente.getValue().getOutputStream());
            output.writeObject(message);
        }
    }
    
    public void sendReservedMessage(Message message) throws IOException {
        for (Map.Entry<String, Socket> cliente : clientesMap.entrySet()) {
            if (message.getReceiver().equals(cliente.getKey())) {
                ObjectOutputStream output = new ObjectOutputStream(cliente.getValue().getOutputStream());
                output.writeObject(message);
            }
        }
    }
    
    public void sendUsersOnline() throws IOException {
        ArrayList<String> OnlineUsers = new ArrayList();
        
        for (Map.Entry<String, Socket> cliente : clientesMap.entrySet()) {
            OnlineUsers.add(cliente.getKey());
        }
        
        Message message = new Message();
        message.setAction(Action.USERS_ONLINE);
        message.setOnlineUsers(OnlineUsers);
        
        for (Map.Entry<String, Socket> cliente : clientesMap.entrySet()) {
            ObjectOutputStream output = new ObjectOutputStream(cliente.getValue().getOutputStream());
            output.writeObject(message);
        }
        
    }
    
}
