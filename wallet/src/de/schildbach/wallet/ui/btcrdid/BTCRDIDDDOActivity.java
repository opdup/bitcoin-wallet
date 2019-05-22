package de.schildbach.wallet.ui.btcrdid;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.SearchEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import de.schildbach.wallet.R;
import de.schildbach.wallet.ui.AbstractWalletActivity;

public class BTCRDIDDDOActivity extends AbstractWalletActivity {

    private TextView ddoLabel;
    String ddo = null;

    private void initView() {
        ddoLabel = findViewById(R.id.ddoLabel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcrdidddo);

        initView();

        Intent intent = getIntent();
        ddo = intent.getStringExtra("ddo");
        if (ddo == null) {
            ddoLabel.setText("DDO not found");
        }else {
            ddoLabel.setText(ddo);
        }
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

}
