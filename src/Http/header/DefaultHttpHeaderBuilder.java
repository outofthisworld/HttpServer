package http.header;

import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

/**
 * Created by daleappleby on 3/10/15.
 */
public class DefaultHttpHeaderBuilder implements DefaultHeader{
    private Header header;

    public DefaultHttpHeaderBuilder(Header header){
        this.header = header;
    }

    @Override
    public DefaultHttpHeaderBuilder setHttpVers(HttpVersion httpVers) {
        return null;
    }

    @Override
    public DefaultHttpHeaderBuilder setStatusCode(HttpStatusCode statusCode) {
        return null;
    }
}
