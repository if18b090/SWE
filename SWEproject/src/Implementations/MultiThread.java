package Implementations;

import Interfaces.Url;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

public class MultiThread implements Runnable{

    private Socket clientSocket;
    //private Url fileName;
    MultiThread(Socket _ClientSocket){
        this.clientSocket= _ClientSocket;
        //this.fileName = fName;
    }

    @Override
    public void run()  {
        try {
            RequestImpl req = new RequestImpl(clientSocket.getInputStream());
            ResponseImpl res = new ResponseImpl();


            //String fileName1 = url.getFileName();

            if(!req.isValid()){
                res.setStatusCode(400);
                res.getStatus();
                clientSocket.close();
            }
            else {
                res.setStatusCode(200);
                res.setContentType("html");
                res.setContent(Files.readAllBytes(Paths.get(System.getProperty("user.dir") +
                        "/SWEproject/src/Resources/files/" +
                        req.getUrl().getFileName())));
            }
            res.send(clientSocket.getOutputStream());

            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
