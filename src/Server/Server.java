package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    static int qtdClientes = 0;
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(54321);

        System.out.println("Port 54321 has been opened!");
        System.out.println("Server, with Thread, waiting to receive messages from multiple clients...");
        while (true) {
            Socket socket;
            socket = serverSocket.accept();
            qtdClientes++;

            //Showing IP address of connected client
            //System.out.println("Client: " + socket.getInetAddress().getHostAddress() + " Connected");

            ThreadServer thread = new ThreadServer(socket);
            thread.setName("Thread Server: " + String.valueOf(qtdClientes));
            thread.start();
        }
    }  
}
