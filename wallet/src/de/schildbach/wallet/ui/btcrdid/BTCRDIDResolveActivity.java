package de.schildbach.wallet.ui.btcrdid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.SearchEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.opdup.btcrserviceclient.BTCRDIDResolver;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.Nullable;
import de.schildbach.wallet.R;
import de.schildbach.wallet.ui.AbstractWalletActivity;

public class BTCRDIDResolveActivity extends AbstractWalletActivity {

    private EditText btcrDidInput;
    private Button resolveButton;
    private WebView jsonView;

    private void initView(){
        btcrDidInput = (EditText) findViewById(R.id.enterBTCRDID);
        resolveButton = (Button) findViewById(R.id.resolveButton);
        jsonView = (WebView) findViewById(R.id.jsonView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcrdidresolve);

        initView();

        resolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    private class ResolveTask extends AsyncTask<String, String, String> {

        private ProgressDialog p;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(BTCRDIDResolveActivity.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            try {
                URL rootUrl = new URL("https://btcr-service.opdup.com");

                BTCRDIDResolver btcrdidResolver = new BTCRDIDResolver(strings[0], rootUrl);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }

}
