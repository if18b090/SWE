package Implementations;

import Interfaces.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

public class MultiThread implements Runnable{

    private Socket clientSocket= null;
    private PluginManagerImpl PluginMgr = null;
    private PluginImpl myPlugin = null;
    //private Url fileName;
    MultiThread(Socket _ClientSocket, PluginManagerImpl PlMgr){
        this.clientSocket= _ClientSocket;
        this.PluginMgr = PlMgr;
        //this.fileName = fName;
    }

    @Override
    public void run()  {
        try {
            Request req = new RequestImpl(clientSocket.getInputStream());
            myPlugin = (PluginImpl) PluginMgr.getPlugins();
            Response res = myPlugin.handle(req);
            if(res == null){
                res = new ResponseImpl();
                res.setStatusCode(404);
            }
            res.send(clientSocket.getOutputStream());
            clientSocket.close();
/*

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
            //clientSocket.close();*/

            } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
