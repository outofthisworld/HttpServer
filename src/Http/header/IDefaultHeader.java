package http.header;

import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

/**
 * Created by daleappleby on 4/10/15.
 */
interface IDefaultHeader {
    public void setHttpVers(HttpVersion httpVers);
    public void setStatusCode(HttpStatusCode statusCode);
    public HttpStatusCode getStatusCode();
    public HttpVersion getHttpVers();
}
