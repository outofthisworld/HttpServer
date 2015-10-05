package http.header;

import http.constants.HttpMethods;
import http.constants.HttpStatusCode;
import http.constants.HttpVersion;
import http.constants.MimeTypes;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by daleappleby on 4/10/15.
 */
public class FullOutgoingHttpHeader extends OutgoingHttpHeader {
    private String accept;

    /**
     * Instantiates a new Full http response header.
     *
     * @param header the header
     */
    public FullOutgoingHttpHeader(HttpHeader header){
      super(header.getHttpVers(),header.getStatusCode());
    }

    /**
     * Instantiates a new Full outgoing http header.
     *
     * @param vers the vers
     */
    public FullOutgoingHttpHeader(HttpVersion vers) {
        super(vers);
    }

    /**
     * Instantiates a new Full outgoing http header.
     *
     * @param httpStatusCode the http status code
     */
    public FullOutgoingHttpHeader(HttpStatusCode httpStatusCode) {
        super(httpStatusCode);
    }

    /**
     * Instantiates a new Full outgoing http header.
     *
     * @param vers the vers
     * @param statusCode the status code
     */
    public FullOutgoingHttpHeader(HttpVersion vers, HttpStatusCode statusCode) {
        super(vers, statusCode);
    }

    /**
     * Instantiates a new Full outgoing http header.
     *
     * @param vers the vers
     * @param statusCode the status code
     * @param date the date
     */
    public FullOutgoingHttpHeader(HttpVersion vers, HttpStatusCode statusCode, Date date) {
        super(vers, statusCode, date);
    }

    /**
     * Instantiates a new Full outgoing http header.
     *
     * @param vers the vers
     * @param statusCode the status code
     * @param mimeTypes the mime types
     */
    public FullOutgoingHttpHeader(HttpVersion vers, HttpStatusCode statusCode, MimeTypes mimeTypes) {
        super(vers, statusCode, mimeTypes);
    }

    /**
     * Instantiates a new Full outgoing http header.
     *
     * @param vers the vers
     * @param statusCode the status code
     * @param date the date
     * @param mimeTypes the mime types
     */
    public FullOutgoingHttpHeader(HttpVersion vers, HttpStatusCode statusCode, Date date, MimeTypes mimeTypes) {
        super(vers, statusCode, date, mimeTypes);
    }

    /**
     * Sets accept.
     *
     * @param accept the accept
     */
    public void setAccept(HttpMethods... accept) {
        this.accept = String.join(",", Arrays.copyOfRange(Stream.of(accept).
                map(e -> e.name()).toArray(), 0, accept.length, String[].class));
    }


    @Override
    public StringBuilder encode() {
        return super.encode();
    }


    @Override
    public HttpHeaderBuilder getHeaderBuilder() {
        return new FullOutgoingHttpHeaderBuilder(this);
    }

    /**
     * The type Full outgoing http header builder.
     */
    public class FullOutgoingHttpHeaderBuilder<T extends FullOutgoingHttpHeader> extends DefaultHttpResponseBuilder {
        /**
         * Instantiates a new Full outgoing http header builder.
         *
         * @param header the header
         */
        public FullOutgoingHttpHeaderBuilder(T header){
            super(header);
        }

        /**
         * Set accept.
         *
         * @param methodTypes the method types
         * @return the full outgoing http header builder
         */
        public FullOutgoingHttpHeaderBuilder setAccept(HttpMethods... methodTypes){
            FullOutgoingHttpHeader.this.setAccept(methodTypes);
            return this;
        }

        @Override
        public StringBuilder encode() {
            return encode();
        }
    }
}
