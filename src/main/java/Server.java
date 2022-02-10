import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static void main(String[] args) throws IOException {
        Socket socket = null;//Creating a socket with value null
        ServerSocket serverSocket = null;

        ClientHandler clientHandler;

        serverSocket = new ServerSocket(6789);
        System.out.println("Server opened on port 6789");

            while(true) {
                {
                    try {

                        socket = serverSocket.accept();
                        clientHandler = new ClientHandler(socket);
                        clientHandler.start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}
