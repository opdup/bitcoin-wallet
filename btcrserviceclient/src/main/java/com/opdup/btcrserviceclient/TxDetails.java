package com.opdup.btcrserviceclient;

import android.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class TxDetails {

    private URL url;

    private JSONObject jsonObject;
    private String txId;
    private Integer utxoIndex;

    public TxDetails(URL url) {
        this.url = url;
        this.jsonObject = new ServiceConnection(this.url).getJsonObject();
    }

    private Pair<String, Integer> getTxDetails() {

        try {
            this.txId = jsonObject.getString("txid");
            this.utxoIndex = jsonObject.getInt("utxo_index");
        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }

        return new Pair<>(txId, utxoIndex);

    }

    //Get TxID
    public String getTxId() {
        return getTxDetails().first;
    }

    //get UTXO index
    public int getUtxoIndex() {
        return getTxDetails().second;
    }

}