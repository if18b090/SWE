package Implementations;

import Interfaces.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;




public class ResponseImpl implements Response {

    private String content;
    private int statusCode;
    private String status;
    private byte[] contentBytes;
    private int contentLength;
    private String contentType;
    private String serverHeader;
    private final static String DEFAULT_SERVER_HEADER = "BIF-SWE1-Server";
    private Map<String, String> headers = new HashMap<>();

    //getting current date and time of the server
    private static String getTime() {
        Date d1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY HH:mm z", Locale.GERMANY);
        df.setTimeZone(df.getTimeZone().getDefault());
        String formattedDate = df.format(d1);
        return formattedDate;
    }



    public ResponseImpl() {
        headers.put("Date", getTime());
        headers.put("Server", DEFAULT_SERVER_HEADER);
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }


    @Override
    public int getContentLength() {
       return contentLength;
    }


    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
        headers.put("Content-Type", contentType);
    }


    @Override
    public int getStatusCode() {
        if (statusCode == 0) {
            try {
                throw new Exception("No status code was set.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusCode;
    }

    @Override
    public void setStatusCode(int status) {
        this.statusCode = status;
        switch (status) {
            case 200: this.status = "200 Ok";
            break;
            case 201:
                this.status = "201 Created";
                break;
            case 202:
                this.status ="202 Accepted";
                break;
            case 301:
                this.status = "301 Moved Permanently";
                break;
            case 304:
                this.status = "302 Found";
                break;
            case 400:
                this.status = "400 Bad Request";
                break;
            case 404:
                this.status ="404 Not Found";
                break;
            case 500:
                this.status = "500 Internal Server Error";
                break;
            case 505:
                this.status = "505 HTTP Version Not Supported";
                break;
        }
    }


    @Override
    public String getStatus() {
        return Integer.toString(statusCode);
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public String getServerHeader() {
        return serverHeader;
    }

    @Override
    public void setServerHeader(String server) {
        headers.put("Server", server);
    }

    @Override
    public void setContent(String content) {
        contentBytes = content.getBytes();
        contentLength = contentBytes.length;
    }


    @Override
    public void setContent(byte[] content) {
        contentBytes = content;
        contentLength = contentBytes.length;

    }

    @Override
    public void setContent(InputStream stream) {
        try {
            contentBytes = stream.readAllBytes();
            contentLength = contentBytes.length;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void send(OutputStream network) {

        System.out.println(contentBytes);
        System.out.println(status);
        try {
            if ( status == null || contentBytes.length == 0) {
                System.out.println("Hi222");
                System.out.println(status);
                System.out.println(contentBytes);
                throw new IllegalAccessException("No status code or content.");
            }

            StringBuilder builder = new StringBuilder();
            builder.append("HTTP/1.1").append(getStatus()).append(System.getProperty("line.separator"));
            builder.append("Content-Length: ").append(getContentLength()).append(System.getProperty("line.separator"));

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.append(entry.getKey()).append(": ").append(entry.getValue()).append(System.getProperty("line.separator"));
            }

            builder.append(System.getProperty("line.separator"));
            network.write(builder.toString().getBytes());
            network.write(contentBytes);
            network.flush();

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
