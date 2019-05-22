package de.schildbach.wallet.ui.btcrdid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.SearchEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opdup.btcrserviceclient.BTCRDIDResolver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.Nullable;
import de.schildbach.wallet.R;
import de.schildbach.wallet.ui.AbstractWalletActivity;

public class BTCRDIDResolverActivity extends AbstractWalletActivity {

    private EditText inputText;
    private Button button;
    private TextView textView;
    private Button getDDOButton;

    private String txRef = null;
    private String ddo = null;

    private void initView() {
        inputText = findViewById(R.id.inputText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.outputPubKey);
        getDDOButton = findViewById(R.id.getDDOButton);
        getDDOButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcrdidresolver);

        initView();

        button.setOnClickListener(new View.OnClickListener() {      // Get Public Key button
            @Override
            public void onClick(View v) {

                txRef = inputText.getText().toString();

                if (txRef.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Error: Input is empty", Toast.LENGTH_SHORT).show();
                }else {
                    if (txRef.length() < 10) {
                        Toast.makeText(getApplicationContext(), "Error: BTCR DID not valid", Toast.LENGTH_SHORT).show();
                    }
                    if (txRef.substring(0, 9).toLowerCase().equals("did:btcr:")) {

                        BTCRDIDTask btcrdidTask = new BTCRDIDTask();
                        btcrdidTask.execute(txRef);

                        inputText.onEditorAction(EditorInfo.IME_ACTION_DONE);

                        getDDOButton.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(getApplicationContext(), "Error: BTCR DID not valid", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        getDDOButton.setOnClickListener(new View.OnClickListener() {        //get DDO button
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BTCRDIDResolverActivity.this, BTCRDIDDDOActivity.class);
                intent.putExtra("ddo", ddo);
                startActivity(intent);
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


    // Inner class AsyncTask
    private class BTCRDIDTask extends AsyncTask<String, String, String> {

        private String publicKey = null;
        private URL rootURL;

        private ProgressDialog p;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(BTCRDIDResolverActivity.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String...params) {
            try {
                rootURL = new URL("https://btcr-service.opdup.com/");

                BTCRDIDResolver btcrdidResolver = new BTCRDIDResolver(params[0], rootURL);
                publicKey = btcrdidResolver.getPublicKey();
                ddo = btcrdidResolver.getDDO();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return publicKey;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (textView != null){
                p.hide();
                if (result == null) {
                    textView.setText("Not found");
                } else {
                    textView.setText(result);
                }
            } else {
                p.show();
            }
        }

    }

}
