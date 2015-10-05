package http.header;

import http.constants.HttpConstants;
import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

/**
 * Created by daleappleby on 4/10/15.
 */
public class HttpHeader extends AbstractHeader  {

    public HttpHeader() {
        super();
    }

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


    public class IncomingHttpHeaderBuilder implements IHeaderEncoder{
        private HttpHeader header;

        public IncomingHttpHeaderBuilder(HttpHeader header){
            this.header = header;
        }

        public IncomingHttpHeaderBuilder setHttpVers(HttpVersion httpVers){
            header.setHttpVers(httpVers);
            return this;
        }

        public IncomingHttpHeaderBuilder  setStatusCode(HttpStatusCode statusCode){
            header.setStatusCode(statusCode);
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DefaultHttpHeaderBuilder)) return false;

            DefaultHttpHeaderBuilder that = (DefaultHttpHeaderBuilder) o;

            if (header != null ? !header.equals(that.header) : that.header != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return header != null ? header.hashCode() : 0;
        }

        @Override
        public StringBuilder encode() {
            return header.encode();
        }

    }
}
