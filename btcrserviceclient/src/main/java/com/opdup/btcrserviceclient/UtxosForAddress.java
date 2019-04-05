package com.opdup.btcrserviceclient;

import java.net.URL;

public class UtxosForAddress {

    private URL url;

    public UtxosForAddress(URL url) {
        this.url = url;
    }

    public String getUtxos() {
        return new ServiceConnection(this.url).getJsonString();
    }

}
