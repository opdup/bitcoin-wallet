package com.opdup.btcrserviceclient;

import java.io.IOException;
import java.net.URL;

public class DecodedTx {

    private URL url;

    public DecodedTx(URL url) {
        this.url = url;
    }

    public String getTxFromTxId() throws IOException {
        ServiceConnection serviceConnection = new ServiceConnection(this.url);
        return serviceConnection.getJsonString();
    }

}
