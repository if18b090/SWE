package Implementations;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private ServerSocket ss;


    public void runServer(int port) throws IOException{

        ss= new ServerSocket(port);
        System.out.println("Server is running on port "+ port );
        System.out.println("Waiting for client...");

        while (!ss.isClosed()) {
            new Thread(new MultiThread(ss.accept())).start();
        }
    }
}

