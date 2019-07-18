package com.opdup.btcrserviceclient;

import org.json.JSONArray;

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