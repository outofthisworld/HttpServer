package http.header;

import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

import java.util.HashMap;

/**
 * Created by daleappleby on 3/10/15.
 */
public abstract class AbstractHeader implements IDefaultHeader, IHeaderEncoder, IDefaultHeaderBuilder {
    private final HashMap<String, String> headerTags = new HashMap<>();
    private HttpVersion httpVers;
    private HttpStatusCode statusCode;

    /**
     * Instantiates a new Abstract header.
     */
    public AbstractHeader() {}


    /**
     * Instantiates a new Abstract header.
     *
     * @param vers the vers
     * @param statusCode the status code
     */
    public AbstractHeader(HttpVersion vers, HttpStatusCode statusCode) {
    }

    /**
     * Instantiates a new Abstract header.
     *
     * @param vers the vers
     */
    public AbstractHeader(HttpVersion vers) {
    }

    /**
     * Instantiates a new Abstract header.
     *
     * @param statusCode the status code
     */
    public AbstractHeader(HttpStatusCode statusCode) {
    }

    /**
     * Gets http vers.
     *
     * @return the http vers
     */
    public HttpVersion getHttpVers() {
        return httpVers;
    }

    @Override
    public void setHttpVers(HttpVersion httpVers) {
        this.httpVers = httpVers;
    }

    /**
     * Gets status code.
     *
     * @return the status code
     */
    public HttpStatusCode getStatusCode() {
        return this.statusCode;
    }

    @Override
    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets header tags.
     *
     * @return the header tags
     */
    public HashMap<String, String> getHeaderTags() {
        return headerTags;
    }

    /**
     * Gets header.
     *
     * @param headerTag the header tag
     * @return the header
     */
    public String getHeader(String headerTag) {
        return headerTags.get(headerTag);
    }

    /**
     * Put header.
     *
     * @param headerTag the header tag
     * @param headerValue the header value
     */
    public void putHeader(String headerTag, String headerValue) {
        headerTags.put(headerTag, headerValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractHeader)) return false;

        AbstractHeader that = (AbstractHeader) o;

        if (headerTags != null ? !headerTags.equals(that.headerTags) : that.headerTags != null) return false;
        if (httpVers != that.httpVers) return false;
        if (statusCode != that.statusCode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = headerTags != null ? headerTags.hashCode() : 0;
        result = 31 * result + (httpVers != null ? httpVers.hashCode() : 0);
        result = 31 * result + (statusCode != null ? statusCode.hashCode() : 0);
        return result;
    }
}
