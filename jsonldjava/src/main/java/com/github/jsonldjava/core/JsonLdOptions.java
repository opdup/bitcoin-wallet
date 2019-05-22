package com.github.jsonldjava.core;

import com.github.jsonldjava.core.JsonLdConsts.Embed;

/**
 * The JsonLdOptions type as specified in the
 * <a href="http://www.w3.org/TR/json-ld-api/#the-jsonldoptions-type">JSON-LD-
 * API specification</a>.
 *
 * @author tristan
 *
 */
public class JsonLdOptions {

    public static final String JSON_LD_1_0 = "json-ld-1.0";

    public static final String JSON_LD_1_1 = "json-ld-1.1";

    public static final boolean DEFAULT_COMPACT_ARRAYS = true;

    public static final String URGNA2012 = "URGNA2012";
    public static final String URDNA2015 = "URDNA2015";

    /**
     * Constructs an instance of JsonLdOptions using an empty base.
     */
    public JsonLdOptions() {
        this("");
    }

    /**
     * Constructs an instance of JsonLdOptions using the given base.
     *
     * @param base
     *            The base IRI for the document.
     */
    public JsonLdOptions(String base) {
        this.setBase(base);
    }

    /**
     * Creates a shallow copy of this JsonLdOptions object.
     *
     * It will share the same DocumentLoader unless that is overridden, and
     * other mutable objects, so it isn't immutable.
     *
     * @return A copy of this JsonLdOptions object.
     */
    /*public JsonLdOptions copy() {
        final JsonLdOptions copy = new JsonLdOptions(base);

        copy.setCompactArrays(compactArrays);
        copy.setExpandContext(expandContext);
        copy.setProcessingMode(processingMode);
        copy.setDocumentLoader(documentLoader);
        copy.setEmbed(embed);
        copy.setExplicit(explicit);
        copy.setOmitDefault(omitDefault);
        copy.setOmitGraph(omitGraph);
        copy.setFrameExpansion(frameExpansion);
        copy.setPruneBlankNodeIdentifiers(pruneBlankNodeIdentifiers);
        copy.setRequireAll(requireAll);
        copy.setAllowContainerSetOnType(allowContainerSetOnType);
        copy.setUseRdfType(useRdfType);
        copy.setUseNativeTypes(useNativeTypes);
        copy.setProduceGeneralizedRdf(produceGeneralizedRdf);
        copy.format = format;
        copy.useNamespaces = useNamespaces;
        copy.outputForm = outputForm;

        return copy;
    }*/

    // Base options : http://www.w3.org/TR/json-ld-api/#idl-def-JsonLdOptions

    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-base
     */
    private String base = null;

    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-compactArrays
     */
    private Boolean compactArrays = DEFAULT_COMPACT_ARRAYS;
    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-expandContext
     */
    private Object expandContext = null;
    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-processingMode
     */
    private String processingMode = JSON_LD_1_0;

    /**
     * Normalitazion algorithm
     */
    private String algorithm = URGNA2012;

    /**
     * http://www.w3.org/TR/json-ld-api/#widl-JsonLdOptions-documentLoader
     */
    private DocumentLoader documentLoader = new DocumentLoader();

    // Frame options : http://json-ld.org/spec/latest/json-ld-framing/

    private Boolean embed = null;
    private Boolean explicit = null;
    private Boolean omitDefault = null;
    //private Boolean omitGraph = false;
    //private Boolean frameExpansion = false;
    //private Boolean pruneBlankNodeIdentifiers = false;
    //private Boolean requireAll = false;
    //private Boolean allowContainerSetOnType = false;

    // RDF conversion options :
    // http://www.w3.org/TR/json-ld-api/#serialize-rdf-as-json-ld-algorithm

    Boolean useRdfType = false;
    Boolean useNativeTypes = false;
    private boolean produceGeneralizedRdf = false;

    public Boolean getEmbed() {
        return embed;
    }

    public void setEmbed(Boolean embed) {
        this.embed = embed;
    }

    public Boolean getExplicit() {
        return explicit;
    }

    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    public Boolean getOmitDefault() {
        return omitDefault;
    }

    public void setOmitDefault(Boolean omitDefault) {
        this.omitDefault = omitDefault;
    }

    public Boolean getCompactArrays() {
        return compactArrays;
    }

    public void setCompactArrays(Boolean compactArrays) {
        this.compactArrays = compactArrays;
    }

    public Object getExpandContext() {
        return expandContext;
    }

    public void setExpandContext(Object expandContext) {
        this.expandContext = expandContext;
    }

    public String getProcessingMode() {
        return processingMode;
    }

    public void setProcessingMode(String processingMode) {
        this.processingMode = processingMode;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Boolean getUseRdfType() {
        return useRdfType;
    }

    public void setUseRdfType(Boolean useRdfType) {
        this.useRdfType = useRdfType;
    }

    public Boolean getUseNativeTypes() {
        return useNativeTypes;
    }

    public void setUseNativeTypes(Boolean useNativeTypes) {
        this.useNativeTypes = useNativeTypes;
    }

    public boolean getProduceGeneralizedRdf() {
        return this.produceGeneralizedRdf;
    }

    public void setProduceGeneralizedRdf(Boolean produceGeneralizedRdf) {
        this.produceGeneralizedRdf = produceGeneralizedRdf;
    }

    public DocumentLoader getDocumentLoader() {
        return documentLoader;
    }

    public void setDocumentLoader(DocumentLoader documentLoader) {
        this.documentLoader = documentLoader;
    }

    // TODO: THE FOLLOWING ONLY EXIST SO I DON'T HAVE TO DELETE A LOT OF CODE,
    // REMOVE IT WHEN DONE
    public String format = null;
    public Boolean useNamespaces = false;
    public String outputForm = null;

}
