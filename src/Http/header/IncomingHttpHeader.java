package http.header;

import http.constants.HttpConstants;
import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

/**
 * Created by daleappleby on 4/10/15.
 */
public class IncomingHttpHeader extends AbstractHeader implements IDefaultHeader {

    public IncomingHttpHeader() {
        super();
    }

    public IncomingHttpHeader(HttpVersion vers, HttpStatusCode statusCode) {
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
    public void setHttpVers(HttpVersion httpVers) {
        putHeader(httpVers.name());
    }

    @Override
    public void setStatusCode(HttpStatusCode statusCode) {

    }


    public class DefaultHttpHeaderBuilder implements IHeaderEncoder{
        private IncomingHttpHeader header;

        public DefaultHttpHeaderBuilder(IncomingHttpHeader header){
            this.header = header;
        }

        public <T extends DefaultHttpHeaderBuilder> T setHttpVers(HttpVersion httpVers){
            header.setHttpVers(httpVers);
            return (T) this;
        }

        public <T extends DefaultHttpHeaderBuilder> T  setStatusCode(HttpStatusCode statusCode){
            header.setStatusCode(statusCode);
            return (T) this;
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
