package com.opdup.btcrserviceclient;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class BTCRDIDResolverTest {

    private MockResponse mockResponse;
    private MockWebServer mockWebServer;

    private BTCRDIDResolver btcrdidResolver;

    public BTCRDIDResolverTest() {
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
    public void testGetDDOForTxref() {

        String btcrDid = "btcr:did:x705-jzv2-qqaz-7vuz";
        String txRef = "x705-jzv2-qqaz-7vuz";
        String pubKey = null;
        String correctPubKey = "02e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49";
        URL url;

        //getTxid response
        mockResponse.setBody("{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"utxo_index\":\"0\"}");
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + txRef + "/txid").url();

        //getTip response
        String bodyGetTip = "[{\"Transaction\":{\"hex\":\"0100000001b6cece07aa40fb30a5810ea0b234e5a515aa2dc05b6f713d6fd9c143f01c8780000000006b483045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b4012103ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262fffffffff0100c5dc03000000001976a9145aeb70a7801a42c849732a2b36727c21f61793f988ac00000000\",\"txid\":\"be5be4b2c4e530b49af139a8448ae2ae8b5882f2e7f5c7908eca0268f72494e9\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"vout\":0,\"scriptSig\":{\"asm\":\"3045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b401 03ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262f\",\"hex\":\"483045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b4012103ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262f\"},\"prevOut\":{\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"],\"value\":0.649},\"sequence\":4294967295}],\"vout\":[{\"value\":0.648,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 5aeb70a7801a42c849732a2b36727c21f61793f9 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9145aeb70a7801a42c849732a2b36727c21f61793f988ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mooh9yq5aV4u5gSR9oRZGfYS9qbydBVLjA\"]}}],\"blockhash\":\"0000000000000011fd70846401c79e3b04ba2f0b75c6d160cbaae71287a9c1b7\",\"confirmations\":57032,\"time\":1531781125,\"blocktime\":1531781125},\"InTipChain\":true}]";
        mockResponse.setBody(bodyGetTip);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + txRef + "/tip").url();

        //resolve response
        String bodyResolve = "[{\"Transaction\":{\"hex\":\"010000000151ace177cd6250209c9da1878e6d8058d04318125d30b1f3bc5c7471925d5f3e010000006b483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49ffffffff01a04bde03000000001976a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac00000000\",\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"3e5f5d9271745cbcf3b1305d121843d058806d8e87a19d9c205062cd77e1ac51\",\"vout\":1,\"scriptSig\":{\"asm\":\"3045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc01 02e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\",\"hex\":\"483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\"},\"prevOut\":{\"addresses\":[\"mo8pqoxeu4EWSisZ8n94niyvPVJzDL3epg\"],\"value\":0.65},\"sequence\":4294967295}],\"vout\":[{\"value\":0.649,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 7055778c9993c43f422a0c963fdf7d2263a7a371 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"]}}],\"blockhash\":\"0000000000000035b43f523828f38f5c97e5e34c524f7971940602d9c7d425ed\",\"confirmations\":57053,\"time\":1531769423,\"blocktime\":1531769423},\"InTipChain\":false},{\"Transaction\":{\"hex\":\"010000000138c9b5aef6444e43697ec87cbe3c34fe54372f7b5200b19f224aaf0445b5ac6f000000006a47304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be524360121020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841ffffffff01e01dbe07000000001976a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac00000000\",\"txid\":\"2f1838f481be7b4f4d37542a751aa3a27be7114f798feb24ff0fc764730973d0\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"6facb54504af4a229fb100527b2f3754fe343cbe7cc87e69434e44f6aeb5c938\",\"vout\":0,\"scriptSig\":{\"asm\":\"304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be5243601 020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841\",\"hex\":\"47304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be524360121020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841\"},\"prevOut\":{\"addresses\":[\"n3oaLYUpxwEnQQS8zcX3zGiLUgFi5dH313\"],\"value\":1.3},\"sequence\":4294967295}],\"vout\":[{\"value\":1.299,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 7055778c9993c43f422a0c963fdf7d2263a7a371 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"]}}],\"blockhash\":\"000000000000005561a0b82b1dd8eefd1c3ba0cf674a67cf6699a3f66410df39\",\"confirmations\":57035,\"time\":1531778011,\"blocktime\":1531778011},\"InTipChain\":false},{\"Transaction\":{\"hex\":\"0100000001b6cece07aa40fb30a5810ea0b234e5a515aa2dc05b6f713d6fd9c143f01c8780000000006b483045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b4012103ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262fffffffff0100c5dc03000000001976a9145aeb70a7801a42c849732a2b36727c21f61793f988ac00000000\",\"txid\":\"be5be4b2c4e530b49af139a8448ae2ae8b5882f2e7f5c7908eca0268f72494e9\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"vout\":0,\"scriptSig\":{\"asm\":\"3045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b401 03ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262f\",\"hex\":\"483045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b4012103ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262f\"},\"prevOut\":{\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"],\"value\":0.649},\"sequence\":4294967295}],\"vout\":[{\"value\":0.648,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 5aeb70a7801a42c849732a2b36727c21f61793f9 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9145aeb70a7801a42c849732a2b36727c21f61793f988ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mooh9yq5aV4u5gSR9oRZGfYS9qbydBVLjA\"]}}],\"blockhash\":\"0000000000000011fd70846401c79e3b04ba2f0b75c6d160cbaae71287a9c1b7\",\"confirmations\":57032,\"time\":1531781125,\"blocktime\":1531781125},\"InTipChain\":true}]";
        mockResponse.setBody(bodyResolve);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + txRef + "/resolve").url();

        btcrdidResolver = new BTCRDIDResolver(btcrDid, url);
        try {
            pubKey = btcrdidResolver.getDDOForTxref();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertTrue(!(pubKey.isEmpty() && pubKey == null));
        assertEquals(correctPubKey, pubKey);
    }

    //Test Resolve
    @Test
    public void testResolve() throws IOException {
        URL url;
        String txRef = "x705-jzv2-qqaz-7vuz";
        String btcrDid = "btcr:did:x705-jzv2-qqaz-7vuz";

        String response = "[{\"Transaction\":{\"hex\":\"010000000151ace177cd6250209c9da1878e6d8058d04318125d30b1f3bc5c7471925d5f3e010000006b483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49ffffffff01a04bde03000000001976a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac00000000\",\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"3e5f5d9271745cbcf3b1305d121843d058806d8e87a19d9c205062cd77e1ac51\",\"vout\":1,\"scriptSig\":{\"asm\":\"3045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc01 02e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\",\"hex\":\"483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\"},\"prevOut\":{\"addresses\":[\"mo8pqoxeu4EWSisZ8n94niyvPVJzDL3epg\"],\"value\":0.65},\"sequence\":4294967295}],\"vout\":[{\"value\":0.649,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 7055778c9993c43f422a0c963fdf7d2263a7a371 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"]}}],\"blockhash\":\"0000000000000035b43f523828f38f5c97e5e34c524f7971940602d9c7d425ed\",\"confirmations\":57053,\"time\":1531769423,\"blocktime\":1531769423},\"InTipChain\":false},{\"Transaction\":{\"hex\":\"010000000138c9b5aef6444e43697ec87cbe3c34fe54372f7b5200b19f224aaf0445b5ac6f000000006a47304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be524360121020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841ffffffff01e01dbe07000000001976a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac00000000\",\"txid\":\"2f1838f481be7b4f4d37542a751aa3a27be7114f798feb24ff0fc764730973d0\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"6facb54504af4a229fb100527b2f3754fe343cbe7cc87e69434e44f6aeb5c938\",\"vout\":0,\"scriptSig\":{\"asm\":\"304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be5243601 020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841\",\"hex\":\"47304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be524360121020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841\"},\"prevOut\":{\"addresses\":[\"n3oaLYUpxwEnQQS8zcX3zGiLUgFi5dH313\"],\"value\":1.3},\"sequence\":4294967295}],\"vout\":[{\"value\":1.299,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 7055778c9993c43f422a0c963fdf7d2263a7a371 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"]}}],\"blockhash\":\"000000000000005561a0b82b1dd8eefd1c3ba0cf674a67cf6699a3f66410df39\",\"confirmations\":57035,\"time\":1531778011,\"blocktime\":1531778011},\"InTipChain\":false},{\"Transaction\":{\"hex\":\"0100000001b6cece07aa40fb30a5810ea0b234e5a515aa2dc05b6f713d6fd9c143f01c8780000000006b483045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b4012103ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262fffffffff0100c5dc03000000001976a9145aeb70a7801a42c849732a2b36727c21f61793f988ac00000000\",\"txid\":\"be5be4b2c4e530b49af139a8448ae2ae8b5882f2e7f5c7908eca0268f72494e9\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"vout\":0,\"scriptSig\":{\"asm\":\"3045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b401 03ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262f\",\"hex\":\"483045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b4012103ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262f\"},\"prevOut\":{\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"],\"value\":0.649},\"sequence\":4294967295}],\"vout\":[{\"value\":0.648,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 5aeb70a7801a42c849732a2b36727c21f61793f9 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9145aeb70a7801a42c849732a2b36727c21f61793f988ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mooh9yq5aV4u5gSR9oRZGfYS9qbydBVLjA\"]}}],\"blockhash\":\"0000000000000011fd70846401c79e3b04ba2f0b75c6d160cbaae71287a9c1b7\",\"confirmations\":57032,\"time\":1531781125,\"blocktime\":1531781125},\"InTipChain\":true}]";

        mockResponse.setBody(response);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + txRef + "/resolve").url();

        btcrdidResolver = new BTCRDIDResolver(btcrDid, url);
        String resolveString = btcrdidResolver.resolve();

        assertTrue(!resolveString.isEmpty());
        assertEquals(resolveString, response);
    }

    // Test getTxDetails
    @Test
    public void testGetTxDetails() throws IOException {
        URL url;
        String txRef = "x705-jzv2-qqaz-7vuz";
        String btcrDid = "btcr:did:x705-jzv2-qqaz-7vuz";

        String response = "{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"utxo_index\":\"0\"}";

        mockResponse.setBody(response);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + txRef + "/txid").url();

        btcrdidResolver = new BTCRDIDResolver(btcrDid, url);
        String txDetailsString = btcrdidResolver.getTxDetails().toString();

        assertTrue(!txDetailsString.isEmpty());
        assertEquals(txDetailsString, response);
    }

    // Test Decode
    @Test
    public void testDecode() throws IOException {
        URL url;
        String txRef = "x705-jzv2-qqaz-7vuz";
        String btcrDid = "btcr:did:x705-jzv2-qqaz-7vuz";

        String response = "{\"Hrp\":\"txtest\",\"Magic\":6,\"Height\":1353983,\"Position\":83,\"UtxoIndex\":0}";

        mockResponse.setBody(response);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + txRef + "/decode").url();

        btcrdidResolver = new BTCRDIDResolver(btcrDid, url);
        String decodeString = btcrdidResolver.decode();

        assertTrue(!decodeString.isEmpty());
        assertEquals(decodeString, response);
    }

    // Test getTxId from TxDetails class
    @Test
    public void testGetTxId() throws IOException {
        URL url;
        String txRef = "x705-jzv2-qqaz-7vuz";
        String btcrDid = "btcr:did:x705-jzv2-qqaz-7vuz";

        String response = "{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"utxo_index\":\"0\"}";
        String txid = "80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6";

        mockResponse.setBody(response);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + txRef + "/decode").url();

        btcrdidResolver = new BTCRDIDResolver(btcrDid, url);
        String txidString = btcrdidResolver.getTxId();

        assertTrue(!txidString.isEmpty());
        assertEquals(txidString, txid);
    }

    // Test getDecodedTx
    @Test
    public void testGetDecodedTx() throws IOException {
        URL url;
        String txRef = "x705-jzv2-qqaz-7vuz";
        String btcrDid = "btcr:did:x705-jzv2-qqaz-7vuz";

        //getTxid response
        mockResponse.setBody("{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"utxo_index\":\"0\"}");
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/txref/" + txRef + "/txid").url();


        String txid = "80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6";
        String response = "{\"hex\":\"010000000151ace177cd6250209c9da1878e6d8058d04318125d30b1f3bc5c7471925d5f3e010000006b483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49ffffffff01a04bde03000000001976a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac00000000\",\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"hash\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"size\":192,\"vsize\":192,\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"3e5f5d9271745cbcf3b1305d121843d058806d8e87a19d9c205062cd77e1ac51\",\"vout\":1,\"scriptSig\":{\"asm\":\"3045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc01 02e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\",\"hex\":\"483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\"},\"sequence\":4294967295}],\"vout\":[{\"value\":0.649,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 7055778c9993c43f422a0c963fdf7d2263a7a371 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"]}}],\"blockhash\":\"0000000000000035b43f523828f38f5c97e5e34c524f7971940602d9c7d425ed\",\"confirmations\":57053,\"time\":1531769423,\"blocktime\":1531769423}";

        mockResponse.setBody(response);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/tx/" + txid).url();

        btcrdidResolver = new BTCRDIDResolver(btcrDid, url);
        String decodedTxString = null;
        try {
            decodedTxString = btcrdidResolver.getDecodedTx();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertTrue(!decodedTxString.isEmpty());
        assertEquals(decodedTxString, response);
    }

    // Test getUtxosForAddress
    @Test
    public void testGetUtxosForAddress() throws IOException {
        URL url;
        String btcrDid = "btcr:did:x705-jzv2-qqaz-7vuz";
        String address = "mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw";
        String response = "[{\"hex\":\"010000000151ace177cd6250209c9da1878e6d8058d04318125d30b1f3bc5c7471925d5f3e010000006b483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49ffffffff01a04bde03000000001976a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac00000000\",\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"3e5f5d9271745cbcf3b1305d121843d058806d8e87a19d9c205062cd77e1ac51\",\"vout\":1,\"scriptSig\":{\"asm\":\"3045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc01 02e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\",\"hex\":\"483045022100fca4787f193c3ec6929a6054e68352c4c085f36ac400832d901d33769084fc3c02205a56132832cfd08112740f044790e6976b3ea1bca76a4f99870eff276c9c9edc012102e0e01a8c302976e1556e95c54146e8464adac8626a5d29474718a7281133ff49\"},\"prevOut\":{\"addresses\":[\"mo8pqoxeu4EWSisZ8n94niyvPVJzDL3epg\"],\"value\":0.65},\"sequence\":4294967295}],\"vout\":[{\"value\":0.649,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 7055778c9993c43f422a0c963fdf7d2263a7a371 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"]}}],\"blockhash\":\"0000000000000035b43f523828f38f5c97e5e34c524f7971940602d9c7d425ed\",\"confirmations\":57053,\"time\":1531769423,\"blocktime\":1531769423},{\"hex\":\"010000000138c9b5aef6444e43697ec87cbe3c34fe54372f7b5200b19f224aaf0445b5ac6f000000006a47304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be524360121020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841ffffffff01e01dbe07000000001976a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac00000000\",\"txid\":\"2f1838f481be7b4f4d37542a751aa3a27be7114f798feb24ff0fc764730973d0\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"6facb54504af4a229fb100527b2f3754fe343cbe7cc87e69434e44f6aeb5c938\",\"vout\":0,\"scriptSig\":{\"asm\":\"304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be5243601 020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841\",\"hex\":\"47304402207126a4516a93f376c3fc3b8922f63ca95f0e472e43bc7c243e3e8d9a3fb070a7022030fa154fa3f56471e77006cb5b3823ea4c7f4c849dedd8ba7df0d5b43be524360121020a5a5c8c3575489cd2c17d43f642fc2b34792d47c9b026fafe33b3469e31b841\"},\"prevOut\":{\"addresses\":[\"n3oaLYUpxwEnQQS8zcX3zGiLUgFi5dH313\"],\"value\":1.3},\"sequence\":4294967295}],\"vout\":[{\"value\":1.299,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 7055778c9993c43f422a0c963fdf7d2263a7a371 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9147055778c9993c43f422a0c963fdf7d2263a7a37188ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"]}}],\"blockhash\":\"000000000000005561a0b82b1dd8eefd1c3ba0cf674a67cf6699a3f66410df39\",\"confirmations\":57035,\"time\":1531778011,\"blocktime\":1531778011},{\"hex\":\"0100000001b6cece07aa40fb30a5810ea0b234e5a515aa2dc05b6f713d6fd9c143f01c8780000000006b483045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b4012103ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262fffffffff0100c5dc03000000001976a9145aeb70a7801a42c849732a2b36727c21f61793f988ac00000000\",\"txid\":\"be5be4b2c4e530b49af139a8448ae2ae8b5882f2e7f5c7908eca0268f72494e9\",\"hash\":\"\",\"size\":\"\",\"vsize\":\"\",\"version\":1,\"locktime\":0,\"vin\":[{\"txid\":\"80871cf043c1d96f3d716f5bc02daa15a5e534b2a00e81a530fb40aa07ceceb6\",\"vout\":0,\"scriptSig\":{\"asm\":\"3045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b401 03ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262f\",\"hex\":\"483045022100ba6a336470cd220ccd62afaeda7a105677bb9e20c27080b67f0bada5c33698b202204f6d1f311244033be88e62c46a36bf0b8af9f91048e5313cf59929a9e2fdf1b4012103ceba0298eb4a026b9d9b1486c90c088a92e15892dd4c4044e3e6ad076c9b262f\"},\"prevOut\":{\"addresses\":[\"mqkvMtYfTufj3iEXjYHmnopZrsowMFxrKw\"],\"value\":0.649},\"sequence\":4294967295}],\"vout\":[{\"value\":0.648,\"n\":0,\"scriptPubKey\":{\"asm\":\"OP_DUP OP_HASH160 5aeb70a7801a42c849732a2b36727c21f61793f9 OP_EQUALVERIFY OP_CHECKSIG\",\"hex\":\"76a9145aeb70a7801a42c849732a2b36727c21f61793f988ac\",\"reqSigs\":1,\"type\":\"pubkeyhash\",\"addresses\":[\"mooh9yq5aV4u5gSR9oRZGfYS9qbydBVLjA\"]}}],\"blockhash\":\"0000000000000011fd70846401c79e3b04ba2f0b75c6d160cbaae71287a9c1b7\",\"confirmations\":57032,\"time\":1531781125,\"blocktime\":1531781125}]";
        mockResponse.setBody(response);
        mockWebServer.enqueue(mockResponse);
        url = mockWebServer.url("/addr/" + address + "/spends").url();

        btcrdidResolver = new BTCRDIDResolver(btcrDid, url);
        String utxos = btcrdidResolver.getUtxosForAddress(address);

        assertTrue(!utxos.isEmpty());
        assertEquals(response, utxos);
    }
}
