
package Implementations;

import Interfaces.Plugin;
import Interfaces.Request;
import Interfaces.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

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
        if(req.getUrl().getRawUrl().matches("/temp/\\d\\d\\d\\d/\\d\\d/\\d\\d/?.*") == true){
            String time = req.getUrl().getRawUrl().split("[/|?]")[2] + "-" +
                    req.getUrl().getRawUrl().split("[/|?]")[3] + "-" +
                    req.getUrl().getRawUrl().split("[/|?]")[4] + " 00:00:00";
            Timestamp timestamp = Timestamp.valueOf(time);
            res.setContent(getTimeQuery(timestamp));
        }else{
            int offset = 0;
            int limit = 20;
            String param = null;
            try {
                param = req.getUrl().getParameter().get("offset");
                if(param != null){
                    offset = Integer.parseInt(req.getUrl().getParameter().get("offset"));
                    if (offset < 0)
                        offset = 0;
                }

            }catch(Exception e){
                offset = 0;
            }try {
                param = req.getUrl().getParameter().get("limit");
                if(param !=null){
                    limit = Integer.parseInt(req.getUrl().getParameter().get("limit"));
                    if(limit < 0) limit = 0;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            res.setContent("<html><body><h1 style='color:green'>Temperaturen: </h1><br>" + getData(limit, offset) + "</body></html>");
        }
        //System.out.println("Result: " + res);
        return res;
    }


    private String getData(int limit, int offset){
        StringBuilder result = new StringBuilder("<table border='1'><tr><td><a>ID&nbsp;&nbsp;</a></td>" +
                "<td><a>Temperature&nbsp;&nbsp;</a></td>" +
                "<td><a>Date&nbsp;&nbsp;</a></td></tr>");
        Connection conn = null;
        PreparedStatement myquery = null;
        ResultSet resultSet = null;

        try {

            Class.forName("org.mariadb.jdbc.Driver");
            conn =  DriverManager.getConnection("jdbc:mariadb://localhost:3306/temp?user=root&password=qNuOtNXJQaphcHaw");
            myquery = conn.prepareStatement("SELECT * FROM temp_table order by datum desc limit ? offset ?;");
            myquery.setInt(1, limit);
            myquery.setInt(2, offset);

            resultSet = myquery.executeQuery();

            int i = 0;
            while (resultSet.next()){

                if(i >= limit)break;
                result.append("<tr><td><a>");
                result.append(resultSet.getString("id"));
                result.append("</a></td><td><a>");
                result.append(resultSet.getString("temp_val"));
                result.append("</a></td><td><a>");
                result.append(resultSet.getString("datum"));
                result.append("</a></td></tr>");
                i++;
                //System.out.println("DB: " + result);
            }
            result.append("</table>");

            if(offset == 0){
                result.append("<a href='?offset=");
                result.append(offset + limit);
                result.append("'>next</a>");
            }else if(offset > 0){
                result.append("<a href='?offset=");
                result.append(offset - limit);
                result.append("'>prev</a>&nbsp;&nbsp;");
                result.append("<a href='?offset=");
                result.append(offset + limit);
                result.append("'>next</a>");
            }
            resultSet.close();
            myquery.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }


    private String getTimeQuery(Timestamp timestamp){
        StringBuilder result = new StringBuilder("<?xml version='1.0'?><temperaturen>");
        Connection conn1 = null;
        PreparedStatement myquery1 = null;
        ResultSet resultSet1 = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn1 = DriverManager.getConnection("jdbc:mariadb://localhost:3306/temp?user=root&password=qNuOtNXJQaphcHaw");
            myquery1 = conn1.prepareStatement("SELECT * FROM temp_table WHERE datum = ? ;");
            myquery1.setTimestamp(1, timestamp);
            timestamp.setTime(timestamp.getTime() + (1000 * 60 * 60 * 24));
            // myquery1.setTimestamp(2, timestamp);

            resultSet1 = myquery1.executeQuery();


            while (resultSet1.next()){
                result.append("<entry><id>");
                result.append(resultSet1.getString("id"));
                result.append("</id><temp>");
                result.append(resultSet1.getString("temp_val"));
                result.append("</temp><date>");
                result.append(resultSet1.getString("datum"));
                result.append("</date></entry>");
                //System.out.println("DB: " + result);
            }
            result.append("</Temperaturen>");

            resultSet1.close();
            myquery1.close();
            conn1.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}


//qNuOtNXJQaphcHaw