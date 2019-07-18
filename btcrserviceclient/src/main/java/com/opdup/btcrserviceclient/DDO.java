package com.opdup.btcrserviceclient;

import com.github.jsonldjava.utils.JsonNormalizer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DDO {

    private String txRef;
    private String pubKey;

    private JSONObject decode;
    private JSONArray resolve;

    public DDO (String txRef, String pubKey, JSONObject decode, JSONArray resolve) {
        this.txRef = txRef;
        this.pubKey = pubKey;
        this.decode = decode;
        this.resolve = resolve;
    }

    private JSONObject getSatoshiAuditTrail() {

        JSONObject satoshi = new JSONObject();

        try {

            String chain = decode.getString("Hrp");
            if (chain.equals("txtest")) {
                chain = "testnet";
            }

            String blockIndex = decode.getString("Position");

            String blockHash;
            String outputIndex;
            String blocktime;
            String time;
            int burnFee;

            JSONObject tx = resolve.getJSONObject(0);

            blockHash = tx.getString("hash");   //######################## Error: no value for blockhash
            time = tx.getString("time");
            blocktime = tx.getString("blocktime");

            JSONArray vout = tx.getJSONArray("vout");
            JSONArray vin = tx.getJSONArray("vin");
            int voutValue = vout.getInt(0);
            JSONArray prevOut = vin.getJSONArray(3);
            int vinValue = prevOut.getInt(1);
            burnFee = voutValue - vinValue;
            outputIndex = vout.getString(1);

            satoshi.put("chain", chain);
            satoshi.put("blockhash", blockHash);
            satoshi.put("blockindex", blockIndex);
            satoshi.put("outputindex", outputIndex);
            satoshi.put("blocktime", blocktime);
            satoshi.put("time", time);
            satoshi.put("timereceived", time);
            satoshi.put("burn-fee", burnFee);

        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }

        return satoshi;
    }

    private JSONObject setDDO() {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("@context", "https://w3id.org/btcr/v1");
            object.put("id", "btcr:did:" + txRef);

            JSONObject pk = new JSONObject();
            pk.put("id", "btcr:did:" + txRef + "#Key01"); // #Key
            pk.put("owner", "btcr:did:" + txRef);
            pk.put("type", "EdDsaSAPublicKeySecp256k1");
            pk.put("publicKeyHex", pubKey);

            object.put("publicKey", pk);

            JSONObject auth = new JSONObject();
            auth.put("type", "EcDsaSAPublicKeySecp256k1Authentication");
            auth.put("publicKey", "#Key01"); // #Key

            object.put("authentication", auth);

            JSONObject service = new JSONObject();
            service.put("type", "BTCREndpoint");
            service.put("serviceEndpoint", "https://raw.githubusercontent.com/kimdhamilton/did/master/ddo.jsonld");

            object.put("service", service);

            JSONObject satoshi = getSatoshiAuditTrail();

            object.put("SatoshiAuditTrail", satoshi);

        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }

        return object;
    }

    public String getDDO() {

        String ddo = "";

        Object document = setDDO();

        new JsonNormalizer(document, new JsonNormalizer.OnNormalizedCompleted() {
            @Override
            public void OnNormalizedComplete(Object object) {
                String normalized = (String) object;
            }
        }).execute();

        /*try {
            ddo = JsonUtils.toPrettyString(document);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }*/

        ddo = document.toString();

        return ddo;
    }

}
