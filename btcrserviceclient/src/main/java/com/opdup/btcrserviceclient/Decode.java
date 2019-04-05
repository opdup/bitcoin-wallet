package com.opdup.btcrserviceclient;

import org.json.JSONObject;

import java.net.URL;

public class Decode {

    private URL url;

    public Decode(URL url){
        this.url = url;
    }

    public JSONObject decode() {
        return new ServiceConnection(this.url).getJsonObject();
    }

}