package com.opdup.btcrserviceclient;

import com.github.jsonldjava.core.DocumentLoader;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private JSONArray getSatoshiAuditTrail() {

        JSONObject satoshi;
        JSONArray satoshiArray = null;

        try {

            String chain = decode.getString("Hrp");
            if (chain.equals("txtest")) {
                chain = "testnet";
            }
            String blockIndex = decode.getString("position");

            String blockHash;
            String outputIndex;
            String blocktime;
            String time;
            //String timeReceveid = "";  // Same as blocktime?
            int burnFee;

            JSONArray txs = resolve.getJSONArray(0);
            JSONObject tx = txs.getJSONObject(0);
            blockHash = tx.getString("blockhash");
            time = tx.getString("time");
            blocktime = tx.getString("blocktime");

            JSONArray vout = tx.getJSONArray("vout");
            JSONArray vin = tx.getJSONArray("vin");
            int voutValue = vout.getInt(0);
            JSONArray prevOut = vin.getJSONArray(3);
            int vinValue = prevOut.getInt(1);
            burnFee = voutValue - vinValue;
            outputIndex = vout.getString(1);

            satoshi = new JSONObject();
            satoshi.put("chain", chain);
            satoshi.put("blockhash", blockHash);
            satoshi.put("blockindex", blockIndex);
            satoshi.put("outputindex", outputIndex);
            satoshi.put("blocktime", blocktime);
            satoshi.put("time", time);
            satoshi.put("timereceived", time);
            satoshi.put("burn-fee", burnFee);

            satoshiArray = new JSONArray(satoshi);
        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }

        return satoshiArray;
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

            JSONArray pkArray = new JSONArray(pk);

            object.put("publicKey", pkArray);

            JSONObject auth = new JSONObject();
            auth.put("type", "EdDsaSAPublicKeySecp256k1Authentication");
            auth.put("publicKey", "#Key01"); // #Key

            JSONArray authArray = new JSONArray(auth);

            object.put("authentication", authArray);

            JSONObject service = new JSONObject();
            service.put("type", "BTCREndpoint");
            service.put("serviceEndpoint", "https://raw.githubusercontent.com/kimdhamilton/did/master/ddo.jsonld");

            JSONArray serviceArray = new JSONArray(service);

            object.put("service", serviceArray);

            JSONArray satoshiArray = getSatoshiAuditTrail();

            object.put("SatoshiAuditTrail", satoshiArray);

        } catch (JSONException e) {
            System.err.print("JSONException: " + e.getMessage());
        }

        return object;
    }

    public String getDDO() {
        String ddo = "";
        try {
            DocumentLoader dl = new DocumentLoader();
            JsonLdOptions options = new JsonLdOptions();
            String jsonContext = "";
            dl.addInjectedDoc("https://w3id.org/btcr/v1", jsonContext);
            options.setDocumentLoader(dl);
            Object jsonObject = JsonUtils.fromString(setDDO().toString());
            Map context = new HashMap();
            Object compact = JsonLdProcessor.compact(jsonObject, context, options);
            ddo = JsonUtils.toPrettyString(compact);
        } catch (IOException e) {
            System.err.print("IOException: " + e.getMessage());
        }
        return ddo;
    }

}
