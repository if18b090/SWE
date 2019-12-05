
import Implementations.Server;
import java.io.IOException;




public class Main {
    public static void main(String[] args) throws IOException {

        //Url url = new UrlImplementation(System.getProperty("user.dir") + "/SWEproject/src/Resources/files/");
        //String getName = url.getFileName();
        //String rawUrl = System.getProperty("user.dir") + "/SWEproject/src/Resources/files/"+ getName;
        System.out.println(System.getProperty("user.dir"));

        Server server = new Server();
        server.runServer(8080);

        }


}
