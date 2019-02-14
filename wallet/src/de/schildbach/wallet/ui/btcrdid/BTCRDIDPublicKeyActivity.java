package de.schildbach.wallet.ui.btcrdid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.opdup.btcrserviceclient.BTCRDIDResolver;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.Nullable;
import de.schildbach.wallet.R;
import de.schildbach.wallet.ui.AbstractWalletActivity;

public class BTCRDIDPublicKeyActivity extends AbstractWalletActivity {

    private EditText inputText;
    private Button button;
    private TextView textView;

    private String txRef = null;

    private void initView() {
        inputText = (EditText) findViewById(R.id.inputText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.outputTextBTCRDID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcrdidpublickey);

        initView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txRef = inputText.getText().toString();

                System.out.println(txRef);

                BTCRDIDTask btcrdidTask = new BTCRDIDTask();
                btcrdidTask.execute(txRef);
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
            p = new ProgressDialog(BTCRDIDPublicKeyActivity.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String...params) {
            try {
                rootURL = new URL("https://btcr-service.opdup.com/");

                System.out.println(params[0]);

                BTCRDIDResolver btcrdidResolver = new BTCRDIDResolver(params[0], rootURL);
                publicKey = btcrdidResolver.getDDOForTxref();

                System.out.print(publicKey);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return publicKey;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (textView!=null){
                p.hide();
                textView.setText(result);
            } else {
                p.show();
            }
        }

    }


}
