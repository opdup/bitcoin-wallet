package com.opdup.btcrserviceclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class Resolve {

    private URL url;

    public Resolve(URL url){
        this.url = url;
    }

    public JSONArray resolve() {
        return new ServiceConnection(this.url).getJsonArray();
    }

}