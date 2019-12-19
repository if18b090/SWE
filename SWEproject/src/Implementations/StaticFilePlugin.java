package Implementations;

import Interfaces.Plugin;
import Interfaces.Request;
import Interfaces.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

public class StaticFilePlugin implements Plugin {
    @Override
    public float canHandle(Request req) {
        System.out.println(req.getUrl().getPath().replaceAll("/", ""));
        try {
            FileReader test = new FileReader(System.getProperty("user.dir") + "/SWEproject/src/Resources/files/"+req.getUrl().getPath().replaceAll("/", ""));
            test.close();
        } catch (IOException e) {
            return 0;
        }
        return 1;
    }

    @Override
    public Response handle(Request req) {

        Response res = new ResponseImpl();
        File file = new File(System.getProperty("user.dir") +
                "/SWEproject/src/Resources/files/" + req.getUrl().getFileName());

        if(!file.exists())
        {
            res.setStatusCode(404);
            try {
                res.setContent(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/SWEproject/src/Resources/files/notFound.gif")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            res.setStatusCode(200);
            res.setContentType("html");
            try {
                res.setContent(Files.readAllBytes(Paths.get(System.getProperty("user.dir") +
                        "/SWEproject/src/Resources/files/" + req.getUrl().getFileName())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
    }


