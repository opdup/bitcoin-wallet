package com.github.jsonldjava.utils;

import android.os.AsyncTask;

import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;

public class JsonNormalizer extends AsyncTask<Void, Void, Void> {

    private JsonLdOptions options;
    private OnNormalizedCompleted listener;
    private Object normalizedObject;
    private Object initialObject;

    public JsonNormalizer(Object object, OnNormalizedCompleted listener) {
        this(object, null, listener);
    }

    public JsonNormalizer(Object object, JsonLdOptions options, OnNormalizedCompleted listener) {
        if (options == null) {
            this.options = new JsonLdOptions();
            this.options.format = "application/nquads";
        }
        this.listener = listener;
        this.initialObject = object;
    }

    public interface OnNormalizedCompleted {
        void OnNormalizedComplete(Object object);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            options.setAlgorithm(JsonLdOptions.URDNA2015);
            this.normalizedObject = JsonLdProcessor.normalize(this.initialObject, this.options);
        } catch (JsonLdError jsonLdError) {
            jsonLdError.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        this.listener.OnNormalizedComplete(this.normalizedObject);

    }

}
