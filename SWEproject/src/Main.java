import Implementations.PluginManagerImpl;
import Implementations.Server;
import Implementations.UrlImplementation;
import Interfaces.PluginManager;
import Interfaces.Url;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        //Url url = new UrlImplementation(System.getProperty("user.dir") + "/SWEproject/src/Resources/files/");
        //String getName = url.getFileName();
        //String rawUrl = System.getProperty("user.dir") + "/SWEproject/src/Resources/files/"+ getName;
        System.out.println(System.getProperty("user.dir"));
        PluginManager PluginMngr = new PluginManagerImpl();

        byte[] b = "GET /temp HTTP1.0\nContent-Length: 10\n\n".getBytes();
        b[b.length-1] = 13;

        Server server = new Server();
        server.runServer(8080);

    }
}
