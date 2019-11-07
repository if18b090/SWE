package Implementations;

import java.io.IOException;
import java.net.Socket;

public class MultiThread implements Runnable{

    private Socket clientSocket;
    MultiThread(Socket _ClientSocket){
        this.clientSocket= _ClientSocket;
    }

    @Override
    public void run()  {
        try {
            RequestImpl req = new RequestImpl(clientSocket.getInputStream());
            ResponseImpl res = new ResponseImpl();
            if(!req.isValid()){
                res.setStatusCode(400);
                res.getStatus();
                clientSocket.close();
            }
            else {
                res.setStatusCode(200);
                res.setContentType("html");
                res.setContent("Hello from BIF-SWE1-Server");
            }
            res.send(clientSocket.getOutputStream());

            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
