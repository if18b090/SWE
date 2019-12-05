package Implementations;

import Interfaces.Plugin;
import Interfaces.Request;
import Interfaces.Response;

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
        if(req.getUrl().getRawUrl().startsWith("/test/") || req.getUrl().getRawUrl().endsWith("/")){
            return (float) 0.5;
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
        res.setContent("Test content");
        return res;
    }
}
