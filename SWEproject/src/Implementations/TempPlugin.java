package Implementations;

import Interfaces.Plugin;
import Interfaces.Request;
import Interfaces.Response;

public class TempPlugin implements Plugin {
    @Override
    public float canHandle(Request req) {
        if(req.getUrl().getRawUrl().startsWith("/temp")){
            return (float) 1;
        }else {
            return 0;
        }
    }

    @Override
    public Response handle(Request req) {
        Response res = new ResponseImpl();
        return null;
    }
}


//qNuOtNXJQaphcHaw