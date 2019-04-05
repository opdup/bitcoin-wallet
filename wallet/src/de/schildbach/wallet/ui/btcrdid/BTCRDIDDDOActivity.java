package de.schildbach.wallet.ui.btcrdid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.SearchEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.opdup.btcrserviceclient.BTCRDIDResolver;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.Nullable;

import de.schildbach.wallet.R;
import de.schildbach.wallet.ui.AbstractWalletActivity;

public class BTCRDIDDDOActivity extends AbstractWalletActivity {

    private String ddoFilePath;

    private WebView webView;
    String pubKey = null;

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcrdidddo);

        initView();

        Intent intent = getIntent();
        String txRef = intent.getStringExtra("txref");
        this.pubKey = intent.getStringExtra("pubkey");

        //DDOTask ddoTask = new DDOTask();
        //ddoTask.execute(txRef);

        /*try {
            final URL rootURL = new URL("https://btcr-service.opdup.com/");

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    BTCRDIDResolver btcrdidResolver = new BTCRDIDResolver(pubKey, rootURL);
                    ddoFilePath = btcrdidResolver.getDDO(pubKey);
                }
            };

            new Thread(runnable).start();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/


        /*if (ddoFilePath.isEmpty()) {
            Toast.makeText(getApplicationContext(), "JSON File not found", Toast.LENGTH_SHORT).show();
        } else {
            webView.loadUrl(ddoFilePath);
        }*/
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return null;
    }

    /*//Inner AsyncTask class
    private class DDOTask extends AsyncTask<String, String, String> {

        private URL rootURL;
        private String filepath;
        *//*private ProgressDialog p;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(BTCRDIDDDOActivity.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }*//*

        @Override
        protected String doInBackground(String... strings) {
            try {
                rootURL = new URL("https://btcr-service.opdup.com/");

                BTCRDIDResolver btcrdidResolver = new BTCRDIDResolver(strings[0], rootURL);

                filepath = btcrdidResolver.getDDO(pubKey);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return filepath;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ddoFilePath = result;
        }
    }*/

}
