package com.opdup.btcrserviceclient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

public class Resolve {

    private URL url;

    public Resolve(URL url){
        this.url = url;
    }

    //Get String
    public String resolve() {
        ServiceConnection serviceConnection = new ServiceConnection(this.url);
        return serviceConnection.getJsonString();
    }

    //Get JSONObject
    public JSONObject resolveJSONObject() {
        ServiceConnection serviceConnection = new ServiceConnection(this.url);
        return serviceConnection.getJsonObject();
    }

    //Get JSONArray
    public JSONArray resolveJSONArray() {
        ServiceConnection serviceConnection = new ServiceConnection(this.url);
        return serviceConnection.getJsonArray();
    }


}