package com.opdup.btcrserviceclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class BTCRDIDResolver {

    private String txRef;

    private URL root;
    private URL endpoint;
    private int TX_REF_SUBSTRING = 9;
    public JSONArray resolveResult;
    public JSONObject decodeResult;
    public String pubKeyResult;

    public BTCRDIDResolver(String btcrDid, URL rootURL) {
        this.root = rootURL;
        this.txRef = "txtest1:" + btcrDid.substring(TX_REF_SUBSTRING);
    }

    //Resolve
    public JSONArray resolve() {
        try {
            this.endpoint = new URL(this.root, "txref/" + this.txRef + "/resolve");
        } catch (MalformedURLException e) {
            System.err.print("MalformedURLException: " + e.getMessage());
        }
        return new Resolve(this.endpoint).resolve();
    }

    //Get TxID
    public String getTxId() {
        try {
            this.endpoint = new URL(this.root, "txref/" + this.txRef + "/txid");
        } catch (MalformedURLException e) {
            System.err.print("MalformedURLException: " + e.getMessage());
        }
        return new TxDetails(this.endpoint).getTxId();
    }

    //get UTXO index
    public int getUtxoIndex() {
        try {
            this.endpoint = new URL(this.root, "txref/" + this.txRef + "/txid");
        } catch (MalformedURLException e) {
            System.err.print("MalformedURLException: " + e.getMessage());
        }
        return new TxDetails(this.endpoint).getUtxoIndex();
    }

    // Following a tip
    public String getTip() {
        JSONArray allTxs = resolve();
        String txid = getTxId();
        if (allTxs == null) {
            return "null";
        }
        if (new Tip(allTxs).followTip(txid)) {
            return "Unspent";
        } else {
            return "Spent";
        }
    }

    // Decode TxRef
    public JSONObject decode() {
        try {
            this.endpoint = new URL(this.root, "txref/" + this.txRef + "/decode");
        } catch (MalformedURLException e) {
            System.err.print("MalformedURLException: " + e.getMessage());
        }
        return new Decode(this.endpoint).decode();
    }

    // Get Decoded Tx from TxId
    public JSONObject getDecodedTx() {
        try {
            this.endpoint = new URL(this.root, "tx/" + getTxId());
        } catch (MalformedURLException e) {
            System.err.print("MalformedURLException: " + e.getMessage());
        }
        return new DecodedTx(this.endpoint).getTxFromTxId();
    }

    //Txid to Utxos for the address in Txid
    public String getUtxosForAddress(String address) {
        try {
            this.endpoint = new URL(this.root, "addr/" + address + "/spends");
        } catch (MalformedURLException e) {
            System.err.print("MalformedURLException: " + e.getMessage());
        }
        return new UtxosForAddress(this.endpoint).getUtxos();
    }

    //Return public key
    public String getPublicKey() {

        String txid = getTxId();
        JSONArray allTxs = resolve();
        String[] values = null;

        String tip = getTip();

        if (tip.equals("null")) {
            return null;
        }
        try {
            for (int i = 0; i < allTxs.length(); i++) {
                JSONObject tx = allTxs.getJSONObject(i).getJSONObject("Transaction");
                String receivedTxId = tx.getString("txid");
                if (receivedTxId.equals(txid)) {

                    int utxoIndex = getUtxoIndex();
                    JSONObject input = tx.getJSONArray("vin").getJSONObject(utxoIndex);
                    JSONObject scriptSig = input.getJSONObject("scriptSig");

                    if (scriptSig == null) {
                        return null;
                    }

                    String asm = scriptSig.getString("asm");

                    if (asm == null) {
                        return null;
                    }

                    values = asm.split("\\s+");
                }
            }
        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }
        return values[1] /*+ " " + tip*/;
    }


    //Return DDO
    public String getDDO() {
        this.resolveResult = resolve();
        this.decodeResult = decode();
        this.pubKeyResult = getPublicKey();
        DDO ddo = new DDO(this.txRef, this.pubKeyResult, this.decodeResult, this.resolveResult);
        return ddo.getDDO();
    }

}
