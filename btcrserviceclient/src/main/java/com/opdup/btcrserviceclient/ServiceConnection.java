package com.opdup.btcrserviceclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

public class ServiceConnection {

    private URL url;
    private HttpURLConnection connection;

    public ServiceConnection(URL url) {
        this.url = url;
    }

    private void connect() {
        try {
            this.connection = (HttpURLConnection) this.url.openConnection();
            this.connection.setDoOutput(false);
            this.connection.setDoInput(true);
            this.connection.setUseCaches(false);
            this.connection.setRequestMethod("GET");
            this.connection.setRequestProperty("Content-Type", "application/json");
            this.connection.setRequestProperty("charset", "utf-8");
            this.connection.connect();
        } catch (SocketException e) {
            System.err.print("SocketException: " + e.getMessage());
        } catch (IOException e) {
            System.err.print("IOException: " + e.getMessage());
        }
    }

    //New getJson method
    public String getJsonString() {
        StringBuilder response = new StringBuilder();
        String responseJSON;
        try {

            connect();

            int status = connection.getResponseCode();

            if (status != 200) {
                throw new IOException("Error: " + status);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }

        } catch (IOException e) {
            System.err.print("IOException: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            responseJSON = response.toString();
        }

        return responseJSON;
    }

    public JSONObject getJsonObject() {

        JSONObject response = null;

        try {
            response = new JSONObject(getJsonString());
        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }

        return response;
    }

    public JSONArray getJsonArray() {

        JSONArray response = null;

        try {
            response = new JSONArray(getJsonString());
        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }

        return response;
    }

}