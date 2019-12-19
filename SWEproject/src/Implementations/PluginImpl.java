package Implementations;

import Interfaces.Plugin;
import Interfaces.Request;
import Interfaces.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PluginImpl implements Plugin {

    /**
     * Returns a score between 0 and 1 to indicate that the plugin is willing to
     * handle the request. The plugin with the highest score will execute the
     * request.
     *
     * @param req
     * @return A score between 0 and 1
     */

    @Override
    public float canHandle(Request req) {
        if(req.getUrl().getRawUrl().startsWith("/")){
            return (float) 1;
        }else {
            return 0;
        }

    }

    /**
     * Called by the server when the plugin should handle the request.
     *
     * @param req
     * @return A new response object.
     */
    @Override
    public Response handle(Request req) {
        Response res = new ResponseImpl();

            String content = "<html>"
                    + "<body>"
                    + "<h1>Main Page\n"
                    + "</h1>";
            res.setContent(content);

        return res;
    }
}
