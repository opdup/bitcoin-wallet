package com.opdup.btcrserviceclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;

public class ServiceConnectionTest {

    private static final int TIMEOUT = 5000;

    private String txRef = "x705-jzv2-qqaz-7vuz";                  //https://github.com/w3c-ccg/did-hackathon-2018/blob/master/BTCR-DID-Tests.md
    private String address = "mo8pqoxeu4EWSisZ8n94niyvPVJzDL3epg"; //https://github.com/w3c-ccg/did-hackathon-2018/blob/master/BTCR-DID-Tests.md
    private String txId = "80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6";   //https://github.com/w3c-ccg/did-hackathon-2018/blob/master/BTCR-DID-Tests.md
    private URL url;

    private ServiceConnection serviceConnection;

    private MockWebServer mockWebServer;
    private MockResponse mockResponse;

    //Constructor
    public ServiceConnectionTest() throws MalformedURLException {
        mockResponse = new MockResponse();
        mockResponse.addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("Cache-Control", "no-cache");
    }

    @Before
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @After
    public void close() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testResolve() throws IOException {
        mockResponse.setBody("{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"utxo_index\":\"0\"}");
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + this.txRef + "/resolve").url();

        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();
        assertTrue(!jsonString.isEmpty());
        assertEquals(jsonString, "{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"utxo_index\":\"0\"}");
    }


    //Test the return of json from btcr-service.opdup.com
    @Test
    public void testResolveFromBtcrserviceOpdup() throws IOException {
        url = new URL("https://btcr-service.opdup.com/txref/txtest1:x705-jzv2-qqaz-7vuz/txid");
        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();

        System.out.print(jsonString); //Json string

        assertTrue(!jsonString.isEmpty());
        assertEquals(jsonString,"{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"utxo_index\":\"0\"}");
    }

    //Test tx details from https://btcr-service.opdup.com/tx/<txid>/txid
    @Test
    public void testTxDetails() throws IOException {
        url = new URL("https://btcr-service.opdup.com/txref/txtest1:x705-jzv2-qqaz-7vuz/txid");
        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String txId = jsonObject.getString("txid");
        }catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println(txId); //print txid

        url = new URL("https://btcr-service.opdup.com/tx/" + txId);
        serviceConnection = new ServiceConnection(url);
        jsonString = serviceConnection.getJsonString();

        System.out.println(jsonString); // tx details
        assertTrue(!jsonString.isEmpty());
        assertEquals(jsonString, "{\"hex\":\"010000000151ace177cd6250209c9da1878e6d8058d04318125d30b1f3bc5c7471925d5f3e010000006b483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49ffffffff01a04bde03000000001976a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac00000000\",\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"hash\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"size\":192,\"vsize\":192,\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"3e5f5d9271745cbcf3b1305d121843d058806d8e87a19d9c205062cd77e1ac51\",\"vout\":1,\"scriptSig\":{\"asm\":\"3045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc01 02e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\",\"hex\":\"483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\"},\"sequence\":4294967295}],\"vout\":[{\"value\":0.649,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 7055778c9993c43f422a0c963fdf7d2263a7a371 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"]}}],\"blockhash\":\"0000000000000035b43f523828f38f5c97e5e34c524f7971940602d9c7d425ed\",\"confirmations\":57053,\"time\":1531769423,\"blocktime\":1531769423}");
    }

    @Test
    public void testFollowTip() throws IOException {
        mockResponse.setBody("{ Following tip }");
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + this.txRef + "/tip").url();

        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();
        assertTrue(!jsonString.isEmpty());
        assertEquals(jsonString, "{ Following tip }");
    }


    //Test follow tip using https://btcr-service.opdup.com
    @Test
    public void testFollowTip2() throws IOException {
        url = new URL("https://btcr-service.opdup.com/txref/txtest1:x705-jzv2-qqaz-7vuz/tip");
        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                boolean inTipChain = jsonObject.getBoolean("InTipChain");

                if (inTipChain) {
                    //DID revoked
                    System.out.println("DID revoked");
                } else {
                    //Show public key from scriptsig
                    String asm = jsonObject.getString("asm");
                    System.out.print(asm);
                    String[] values = asm.split("\\s*");
                    String publicKey = values[1];
                    System.out.println(publicKey);
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testDecodeTxRef() throws IOException {
        mockResponse.setBody("{ Decoded Txref }");
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + this.txRef + "/decode").url();

        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();
        assertTrue(!jsonString.isEmpty());
        assertEquals(jsonString, "{ Decoded Txref }");
    }

    @Test
    public void testTxidFromTxref() throws IOException {
        mockResponse.setBody("{ Get TxId from TxRef }");
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + this.txRef + "/txid").url();

        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();
        assertTrue(!jsonString.isEmpty());
        assertEquals(jsonString, "{ Get TxId from TxRef }");
    }

    @Test
    public void testDecodedTxFromTxref() throws IOException {
        mockResponse.setBody("{ Get Decoded Tx from TxRef }");
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/tx/" + this.txId).url();

        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();
        assertTrue(!jsonString.isEmpty());
        assertEquals(jsonString, "{ Get Decoded Tx from TxRef }");
    }

    @Test
    public void testGetUtxosForAddress() throws IOException {
        mockResponse.setBody("{ Get UTXOs for Address }");
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/addr/" + this.address + "/spends").url();

        serviceConnection = new ServiceConnection(url);
        String jsonString = serviceConnection.getJsonString();
        assertTrue(!jsonString.isEmpty());
        assertEquals(jsonString, "{ Get UTXOs for Address }");
    }

    @Test(timeout = TIMEOUT)
    public void testServerTimeout() {
        mockResponse.setSocketPolicy(SocketPolicy.NO_RESPONSE);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref" + this.txRef + "/resolve").url();
        serviceConnection = new ServiceConnection(url);
    }

}
