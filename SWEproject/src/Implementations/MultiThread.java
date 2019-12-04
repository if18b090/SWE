package Implementations;

import Interfaces.Url;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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

            if(!req.isValid()){
                res.setStatusCode(400);
                res.getStatus();
                clientSocket.close();
            }
            else {

                File file = new File(System.getProperty("user.dir") +
                        "/SWEproject/src/Resources/files/" + req.getUrl().getFileName());

                if(!file.exists())
                {
                    res.setStatusCode(404);
                    res.setContent(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/SWEproject/src/Resources/files/notFound.gif")));
                }
                else {
                    res.setStatusCode(200);
                    res.setContentType("html");
                    res.setContent(Files.readAllBytes(Paths.get(System.getProperty("user.dir") +
                            "/SWEproject/src/Resources/files/" + req.getUrl().getFileName())));
                }

            }
            res.send(clientSocket.getOutputStream());
            //clientSocket.close();

            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
