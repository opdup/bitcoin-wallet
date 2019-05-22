package com.github.jsonldjava.core;

/**
 * Encapsulates a URL along with the parsed resource matching the URL.
 *
 * @author Tristan King
 */
public class RemoteDocument {
/*
    //private final String documentUrl;
    //private final Object document;

    *//**
     * Create a new RemoteDocument with the URL and the parsed resource for the
     * document.
     *
     * @param url
     *            The URL
     * @param document
     *            The parsed resource for the document
     *//*
    *//*public RemoteDocument(String url, Object document) {
        this.documentUrl = url;
        this.document = document;
    }*//*

    *//**
     * Get the URL for this document.
     *
     * @return The URL for this document, as a String
     *//*
    public String getDocumentUrl() {
        return documentUrl;
    }

    *//**
     * Get the parsed resource for this document.
     *
     * @return The parsed resource for this document
     *//*
    public Object getDocument() {
        return document;
    }*/


    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public Object getDocument() {
        return document;
    }

    public void setDocument(Object document) {
        this.document = document;
    }

    public String getContextUrl() {
        return contextUrl;
    }

    public void setContextUrl(String contextUrl) {
        this.contextUrl = contextUrl;
    }

    String documentUrl;
    Object document;
    String contextUrl;

    public RemoteDocument(String url, Object document) {
        this(url, document, null);
    }

    public RemoteDocument(String url, Object document, String context) {
        this.documentUrl = url;
        this.document = document;
        this.contextUrl = context;
    }

}
