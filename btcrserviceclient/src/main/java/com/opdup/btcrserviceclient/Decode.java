package com.opdup.btcrserviceclient;

import java.io.IOException;
import java.net.URL;

public class Decode {

    private URL url;

    public Decode(URL url){
        this.url = url;
    }

    public String decode() throws IOException{
        return new ServiceConnection(this.url).getJsonString();
    }

}