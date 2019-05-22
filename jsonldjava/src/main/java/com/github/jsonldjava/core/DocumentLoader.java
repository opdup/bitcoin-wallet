package com.github.jsonldjava.core;

import com.github.jsonldjava.utils.JsonUtils;

import java.net.URL;

/**
 * Resolves URLs to {@link RemoteDocument}s. Subclass this class to change the
 * behaviour of loadDocument to suit your purposes.
 */
public class DocumentLoader {

    //private final Map<String, Object> m_injectedDocs = new HashMap<>();

    /**
     * Identifies a system property that can be set to "true" in order to
     * disallow remote context loading.
     */
    public static final String DISALLOW_REMOTE_CONTEXT_LOADING = "com.github.jsonldjava.disallowRemoteContextLoading";


    /**
     * Loads the URL if possible, returning it as a RemoteDocument.
     *
     * @param url
     *            The URL to load
     * @return The resolved URL as a RemoteDocument
     * @throws JsonLdError
     *             If there are errors loading or remote context loading has
     *             been disallowed.
     */
    public RemoteDocument loadDocument(String url) throws JsonLdError {

        final String disallowRemote = System
                .getProperty(DocumentLoader.DISALLOW_REMOTE_CONTEXT_LOADING);

        if ("true".equalsIgnoreCase(disallowRemote)) {
            throw new JsonLdError(JsonLdError.Error.LOADING_REMOTE_CONTEXT_FAILED, "Remote context loading has been disallowed (url was " + url + ")");
        }

        final RemoteDocument doc = new RemoteDocument(url, null);
        try {
            doc.setDocument(JsonUtils.fromURL(new URL(url)/*, getHttpClient()*/));
        } catch (final Exception e) {
            throw new JsonLdError(JsonLdError.Error.LOADING_REMOTE_CONTEXT_FAILED, url, e);
        }
        return doc;
    }

    /**
     * An HTTP Accept header that prefers JSONLD.
     *
     * @deprecated Use {@link JsonUtils#ACCEPT_HEADER} instead.
     */
    @Deprecated
    public static final String ACCEPT_HEADER = JsonUtils.ACCEPT_HEADER;

}
