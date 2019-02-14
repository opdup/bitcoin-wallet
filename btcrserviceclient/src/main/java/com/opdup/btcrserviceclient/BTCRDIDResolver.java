package com.opdup.btcrserviceclient;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BTCRDIDResolver {

    private String btcrDid;
    private String txRef;

    private URL root;
    private URL endpoint;
    private String PROTOCOL = "https";
    private String ADDRESS = "localhost";
    private String PORT = "8080";
    private int TX_REF_SUBSTRING = 9;

    //Constructor
    public BTCRDIDResolver(String btcrDid) throws MalformedURLException {
        this.root = new URL(this.PROTOCOL, this.ADDRESS, this.PORT);
        this.btcrDid = btcrDid;
        this.txRef = getTxRef(btcrDid);
    }

    public BTCRDIDResolver(String btcrDid, URL rootURl) {
        this.root = rootURl;
        this.btcrDid = btcrDid;
        this.txRef = getTxRef(btcrDid);
    }

    // Resolve BTCR DID
    public String resolve() throws IOException {
        this.endpoint = new URL(this.root, "txref/" + this.txRef + "/resolve");
        return new Resolve(this.endpoint).resolve();
    }

    //Resolve returning JSONObject
    public JSONObject resolveJSONObject() throws IOException {
        this.endpoint = new URL(this.root, "txref/" + this.txRef + "/resolve");
        return new Resolve(this.endpoint).resolveJSONObject();
    }

    //Resolve returning JSONArray
    public JSONArray resolveJSONArray() throws IOException {
        this.endpoint = new URL(this.root, "txref/" + this.txRef + "/resolve");
        return new Resolve(this.endpoint).resolveJSONArray();
    }

    // Get Tx Details
    public Pair<String, Integer> getTxDetails() throws IOException {
        this.endpoint = new URL(this.root, "txref/" + this.txRef + "/txid");
        return new TxDetails(this.endpoint).getTxDetails();
    }

    //Get TxID
    public String getTxId() {
        try {
            return getTxDetails().first;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //get UTXO index
    private int getUxoIndex() throws IOException {
        return getTxDetails().second;
    }

    // Following a tip
    public String getTip() throws IOException, JSONException {
        this.endpoint = new URL(this.root, "txref/" + this.txRef + "/tip");
        return new Tip(this.endpoint).getTip();
    }

    // Decode TxRef
    public String decode() throws IOException {
        this.endpoint = new URL(this.root, "txref/" + this.txRef + "/decode");
        return new Decode(this.endpoint).decode();
    }

    // Get Decoded Tx from TxId
    public String getDecodedTx() throws IOException {
        String txId = getTxDetails().first;
        this.endpoint = new URL(this.root, "tx/" + txId);
        return new DecodedTx(this.endpoint).getTxFromTxId();
    }

    //Txid to Utxos for the address in Txid
    public String getUtxosForAddress(String address) throws IOException {
        this.endpoint = new URL(this.root, "addr/" + address + "/spends");
        return new UtxosForAddress(this.endpoint).getUtxos();
    }

    //Get the TxRef from BTCR DID
    private String getTxRef(String btcrDid){
        return "txtest1:" + btcrDid.substring(TX_REF_SUBSTRING);
    }

    //Return public key
    public String getDDOForTxref() throws IOException, JSONException {

        String txid = getTxId();
        JSONArray allTxs = resolveJSONArray();

        for (int i = 0; i < allTxs.length(); i++){
            JSONObject tx = allTxs.getJSONObject(i).getJSONObject("Transaction");
            String receivedTxId = tx.getString("txid");
            if (receivedTxId.equals(txid)){

                // TODO: Change to index received from txid call
                int utxoIndex = getUxoIndex();
                JSONObject input = tx.getJSONArray("vin").getJSONObject(utxoIndex);
                final JSONObject scriptSig = input.getJSONObject("scriptSig");

                String asm;

                // TODO use optionals

                if (scriptSig == null){
                    return null;
                }

                asm = scriptSig.getString("asm");

                if (asm == null){
                    return null;
                }

                String[] values = asm.split("\\s+");
                return values[1];
            }
        }

        return null;
    }

}
