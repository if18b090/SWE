package Implementations;

import Interfaces.Request;
import Interfaces.Url;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class RequestImpl implements Request {

    private InputStream inputStream;
    private String method;
    private String httpVer;
    private UrlImplementation url ;
    private Map<String, String>headers = new HashMap<>();

    public RequestImpl(InputStream in) throws IOException, IllegalAccessException {
        BufferedReader brReader = new BufferedReader(new InputStreamReader(in));

        String reader =brReader.readLine();
        String[] segments = reader.split(" ");
        method = segments[0].toUpperCase();
        url = new UrlImplementation(segments[1]);
        httpVer = segments[2];

        while (reader != null && !reader.isEmpty()){
                reader = brReader.readLine();
                StringBuilder str = new StringBuilder(reader);

            for (int i = 0; i < str.length(); i++){
                String parts = str.toString();
                String[] p = parts.split(": ");
                this.headers.put(p[0].toLowerCase(), p[1]);
            }
        }

    }

    @Override
    public boolean isValid() {

        if(method.length() != 3){
            return false;
        }
        return true;
    }

    @Override
    public String getMethod() {
        if(this.isValid()){
            return method;
        }else {
            return null;
        }
    }


    @Override
    public Url getUrl() {
        return url;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public int getHeaderCount() {
        return headers.size();
    }

    @Override
    public String getUserAgent() {
        if(headers.size() > 0){
            return headers.get("user-agent");
        }
        return null;
    }

    @Override
    public int getContentLength() {
        return Integer.parseInt(headers.get("Content-length"));
    }

    @Override
    public String getContentType() {
        if(headers.size() > 0){
            return headers.get("content-type");
        }
        return null;
    }

    @Override
    public InputStream getContentStream() {
        return this.inputStream;
    }


    @Override
    public String getContentString() {
        if(inputStream != null)
        {
            try {
                return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return null;
    }

    @Override
    public byte[] getContentBytes() throws IOException {
        return IOUtils.toByteArray(inputStream);
    }

}
