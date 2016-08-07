package edu.byu.cs.familymap.webAccess;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Handle connection to the FamilyMap server.
 * Created by chris on 8/3/2016.
 */
public class HttpClient {
    private String host;
    private String port;

    public String logIn(String username, String password, String host, String port)
    {
        try
        {
            this.host=host;
            this.port=port;
            URL url=new URL("http://"+host+":"+port+"/user/login");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();

            String postData="{username:\""+username+"\", password:\""+password+"\"}";

            OutputStream requestBody=connection.getOutputStream();
            requestBody.write(postData.getBytes());

            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){

                InputStream responseBody=connection.getInputStream();

                return processResponse(responseBody);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String[] syncData(String authorization)
    {
        String[] result=new String[2];
        try
        {
            URL url=new URL("http://"+host+":"+port+"/person/");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization",authorization);
            connection.connect();

            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){

                InputStream responseBody=connection.getInputStream();

                result[0]=processResponse(responseBody);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            URL url=new URL("http://"+host+":"+port+"/event/");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Authorization",authorization);
            connection.connect();

            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){

                InputStream responseBody=connection.getInputStream();

                result[1]=processResponse(responseBody);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    private String processResponse(InputStream input) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = input.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }

        return baos.toString();
    }

}
