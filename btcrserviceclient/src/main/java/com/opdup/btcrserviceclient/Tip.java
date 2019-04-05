package com.opdup.btcrserviceclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tip {

    private JSONArray allTxs;

    public Tip(JSONArray allTxs){
        this.allTxs = allTxs;
    }

    public boolean followTip(String txid) {
        try {
            for (int i = 1; i < this.allTxs.length(); i++) {
                JSONObject tx = this.allTxs.getJSONObject(i).getJSONObject("Transaction");
                JSONArray vin = tx.getJSONArray("vin");
                String txidVin = vin.getJSONObject(0).getString("txid");

                if (txid.equals(txidVin)) {
                    return true;            //Transaction has been spent
                }

            }
        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }
        return false;
    }

}