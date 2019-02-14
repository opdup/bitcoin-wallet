package com.opdup.btcrserviceclient;

import java.io.IOException;
import java.net.URL;

public class UtxosForAddress {

    private URL url;

    public UtxosForAddress(URL url) {
        this.url = url;
    }

    public String getUtxos() throws IOException {
        ServiceConnection serviceConnection = new ServiceConnection(this.url);
        return serviceConnection.getJsonString();
    }

}
