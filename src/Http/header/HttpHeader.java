package http.header;

import http.constants.HttpConstants;
import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

/**
 * Created by daleappleby on 4/10/15.
 */
public class HttpHeader extends AbstractHeader  {

    /**
     * Instantiates a new Http header.
     */
    public HttpHeader() {
        super();
    }

    /**
     * Instantiates a new Http header.
     *
     * @param vers the vers
     */
    public HttpHeader(HttpVersion vers) {
        super(vers);
    }

    /**
     * Instantiates a new Http header.
     *
     * @param statusCode the status code
     */
    public HttpHeader(HttpStatusCode statusCode) {
        super(statusCode);
    }

    /**
     * Instantiates a new Http header.
     *
     * @param vers the vers
     * @param statusCode the status code
     */
    public HttpHeader(HttpVersion vers, HttpStatusCode statusCode) {
        super(vers, statusCode);
    }

    @Override
    public StringBuilder encode() {
        return new StringBuilder().append(getHttpVers().version).
                append(HttpConstants.SPACE).append(getStatusCode().code).
                append(HttpConstants.SPACE).append(getStatusCode().partName).
                append(HttpConstants.NEWLINE);
    }

    @Override
    public HttpHeaderBuilder getHeaderBuilder() {
        return new HttpHeaderBuilder(this);
    }

    /**
     * The type Http header builder.
     * @param <T>  the type parameter
     */
    public class HttpHeaderBuilder<T extends AbstractHeader> implements IHeaderEncoder{
        private T $instance;

        /**
         * Instantiates a new Http header builder.
         * Note: this class takes in an instance of the outer class in so that it can be initiated
         * from outside this class.
         * @param header the header
         */
        public HttpHeaderBuilder(T header){
            this.$instance = header;
        }

        /**
         * Set http vers.
         *
         * @param httpVers the http vers
         * @return the http header builder
         */
        public HttpHeaderBuilder setHttpVers(HttpVersion httpVers){
            $instance.setHttpVers(httpVers);
            return this;
        }

        /**
         * Set status code.
         *
         * @param statusCode the status code
         * @return the http header builder
         */
        public HttpHeaderBuilder setStatusCode(HttpStatusCode statusCode){
            $instance.setStatusCode(statusCode);
            return this;
        }

        @Override
        public StringBuilder encode() {
            return $instance.encode();
        }
    }
}
