package Implementations;

import Interfaces.Plugin;
import Interfaces.Request;
import Interfaces.Response;
import com.mysql.jdbc.StringUtils;

import java.io.*;
import java.net.URLDecoder;

public class ToLowerPlugin implements Plugin {
    @Override
    public float canHandle(Request req) {
        if (req.getUrl().getRawUrl().startsWith("/tolower")) {
            return (float) 1;
        } else {
            return 0;
        }
    }

    @Override
    public Response handle(Request req) {

        Response res = new ResponseImpl();
        if (req.getMethod().toLowerCase().equals("get")) {
            if (req.getUrl().getParameter().get("text") == null) {
                res.setStatusCode(200);
                res.setContent("<html><head></head><body>" +
                        "<form <action='tolower' method='post'> text: <input type='text' name='text'><input type='submit' value='submit'></form>" +
                        "<br></body></html>");
            } else {
                res.setStatusCode(200);
                res.setContent("<html><head></head><body>" +
                        "<form <action='tolower' method='POST'>Text: <input type='text' name='text'><input type='submit' value='submit'></form>" +
                        req.getUrl().getParameter().get("text").toLowerCase() +
                        "<br></body></html>");
            }
        } else if (req.getMethod().toLowerCase().equals("post")) {
            if (req.getContentLength() > 0) {
                char c = 0;
                String mycontent = "";

                for (int i = 0; i < req.getContentLength(); i++) {
                    try {
                        c = (char) req.getContentStream().read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (c == '+') {
                        mycontent += ' ';
                    } else {
                        mycontent += c;
                    }
                    res.setStatusCode(200);
                    res.setContent("<html><head></head><body>" +
                            "<form <action='tolower' method='POST'> Text: <input type='text' name='text'><input type='submit' value='Submit'>" +
                            "</form><br><pre>" +
                            mycontent.toLowerCase().replaceFirst("Text=", "")
                            + "</pre></body></html>");
                }
            }
        } else {
            res.setStatusCode(200);
            res.setContent("<html><head></head><body>" +
                    "<form <action='tolower' method='POST'> Text: <input type='text' name='text'><input type='submit' value='Submit'>" +
                    "</form> </body></html>");
        }

        System.out.println(res);
        return res;
    }
}




