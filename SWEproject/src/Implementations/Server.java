package Implementations;

import Interfaces.Url;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket ss;
    private Socket ClientSocket;
    PluginManagerImpl PluginMgr = new PluginManagerImpl();


    public void runServer(int port) throws IOException{

        ss= new ServerSocket(port);
        System.out.println("Server is running on port "+ port );
        System.out.println("Waiting for client...");

        while (!ss.isClosed()) {
            new Thread(new MultiThread(ss.accept(), PluginMgr)).start();
        }
    }
}

