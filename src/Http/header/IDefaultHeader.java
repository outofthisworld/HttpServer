package http.header;

import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

/**
 * Created by daleappleby on 4/10/15.
 */
public interface IDefaultHeader {
    public <T extends IDefaultHttpHeaderBuilder> T setHttpVers(HttpVersion httpVers);
    public <T extends IDefaultHttpHeaderBuilder> T setStatusCode(HttpStatusCode statusCode);
}
