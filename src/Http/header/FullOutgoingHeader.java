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
public class FullOutgoingHeader extends IncomingHttpResponseHeader {
    private String accept;

    /**
     * Instantiates a new Full http response header.
     */
    public FullOutgoingHeader(){}

    /**
     * Instantiates a new Full http response header.
     *
     * @param httpStatusCode the http status code
     * @param vers the vers
     */
    public FullOutgoingHeader(HttpStatusCode httpStatusCode, HttpVersion vers){
        setHttpVers(vers);
        setStatusCode(httpStatusCode);
    }

    /**
     * Instantiates a new Full http response header.
     *
     * @param httpStatusCode the http status code
     * @param vers the vers
     * @param date the date
     */
    public FullOutgoingHeader(HttpStatusCode httpStatusCode, HttpVersion vers, Date date){
        setHttpVers(vers);
        setStatusCode(httpStatusCode);
        setDate(date);
    }


    /**
     * Instantiates a new Full http response header.
     *
     * @param httpStatusCode the http status code
     * @param vers the vers
     * @param date the date
     * @param mimeTypes the mime types
     */
    public FullOutgoingHeader(HttpStatusCode httpStatusCode, HttpVersion vers, Date date, MimeTypes[] mimeTypes){
        setHttpVers(vers);
        setStatusCode(httpStatusCode);
        setDate(date);
        setContentType(mimeTypes);
    }

    /**
     * Instantiates a new Full http response header.
     *
     * @param httpStatusCode the http status code
     * @param vers the vers
     * @param date the date
     * @param mimeTypes the mime types
     * @param httpMethods the http methods
     */
    public FullOutgoingHeader(HttpStatusCode httpStatusCode, HttpVersion vers, Date date, MimeTypes[] mimeTypes, HttpMethods[] httpMethods){
        setHttpVers(vers);
        setStatusCode(httpStatusCode);
        setDate(date);
        setContentType(mimeTypes);
        setAccept(httpMethods);
    }

    /**
     * Instantiates a new Full http response header.
     *
     * @param header the header
     */
    public FullOutgoingHeader(HttpHeader header){
      setHttpVers(header.getHttpVers());
      setStatusCode(header.getStatusCode());
    }

    /**
     * Sets accept.
     *
     * @param accept the accept
     */
    public void setAccept(HttpMethods... accept) {
        this.accept = String.join(",", Arrays.copyOfRange(
                        Stream.of(accept).map(e -> e.name()).toArray(), 0, accept.length, String[].class)
        );
    }


    @Override
    public StringBuilder encode() {
        return super.encode();
    }


    public class FullHttpResponseBuilder extends DefaultHttpResponseBuilder {
        private FullOutgoingHeader header;

        public FullHttpResponseBuilder(FullOutgoingHeader header) {
            super(header);
            this.header = header;
        }

        public FullHttpResponseBuilder setAccept(HttpMethods... methodTypes){
            header.setAccept(methodTypes);
            return this;
        }

        @Override
        public StringBuilder encode() {
            return encode();
        }
    }
}
