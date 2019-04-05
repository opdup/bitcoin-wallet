package com.opdup.btcrserviceclient;

import org.json.JSONObject;

import java.net.URL;

public class DecodedTx {

    private URL url;

    public DecodedTx(URL url) {
        this.url = url;
    }

    public JSONObject getTxFromTxId() {
        return new ServiceConnection(this.url).getJsonObject();
    }

}
