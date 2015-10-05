package http.header;

import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

/**
 * Created by daleappleby on 3/10/15.
 */
public class IDefaultHttpHeaderBuilder implements IDefaultHeader {
    private HeaderI header;

    public IDefaultHttpHeaderBuilder(HeaderI header){
        this.header = header;
    }

    @Override
    public <T extends IDefaultHttpHeaderBuilder> T setHttpVers(HttpVersion httpVers) {
        header.setHttpVers(httpVers);
        return (T) this;
    }

    @Override
    public <T extends IDefaultHttpHeaderBuilder> T setStatusCode(HttpStatusCode statusCode) {
        header.setStatusCode(statusCode);
        return (T) this;
    }
}
