package Implementations;

import Interfaces.Plugin;
import Interfaces.Request;
import Interfaces.Response;

public class ToLowerPlugin implements Plugin {
    @Override
    public float canHandle(Request req) {
        return 0;
    }

    @Override
    public Response handle(Request req) {
        return null;
    }
}
