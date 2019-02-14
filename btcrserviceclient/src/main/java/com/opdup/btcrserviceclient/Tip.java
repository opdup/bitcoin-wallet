package com.opdup.btcrserviceclient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class Tip {

    private URL url;

    private JSONArray jsonArray;
    private JSONObject jsonObject;

    private String tipJsonString = null;

    public Tip(URL url){
        this.url = url;
    }

    private String getTipJsonString() throws IOException {
        return new ServiceConnection(this.url).getJsonString();
    }

    public String getTip() throws JSONException, IOException {
        this.tipJsonString = getTipJsonString();
        this.jsonArray = new JSONArray(tipJsonString);
        for (int i = 0; i <jsonArray.length(); i++){
            this.jsonObject = jsonArray.getJSONObject(i);
            boolean inTipChain = jsonObject.getBoolean("InTipChain");
            if (inTipChain) {
                return null; // TODO: Use Java Optionals instead of returning null.
            }else{
                return tipJsonString;
            }


        }
        return tipJsonString;
    }

}