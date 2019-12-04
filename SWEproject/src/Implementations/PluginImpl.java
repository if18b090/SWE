package Implementations;

import Interfaces.Plugin;
import Interfaces.Request;
import Interfaces.Response;

public class PluginImpl implements Plugin {

    @Override
    public float canHandle(Request req) {
        if(req.getUrl().getRawUrl().startsWith("/test/") || req.getUrl().getRawUrl().endsWith("/")){
            return (float) 0.5;
        }else {
            return 0;
        }

    }

    @Override
    public Response handle(Request req) {
        Response res = new ResponseImpl();
        res.setContent("Test content");
        return res;
    }
}
