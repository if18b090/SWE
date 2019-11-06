package Implementations;

import java.io.IOException;
import java.net.Socket;

public class MultiThread implements Runnable{

    private Socket clientSocket;
    public MultiThread(Socket _ClientSocket){
        this.clientSocket= _ClientSocket;
    }

    @Override
    public void run() {
        try {
            RequestImpl req = new RequestImpl(clientSocket.getInputStream());
            if(!req.isValid()){
                clientSocket.close();
            }

            ResponseImpl res = new ResponseImpl();
            res.setStatusCode(200);
            res.setContentType("html");
            res.setContent("Hello from BIF-SWE1-Server");
            res.send(clientSocket.getOutputStream());

            } catch (IOException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}
