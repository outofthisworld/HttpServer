package http.header;

import http.constants.HttpStatusCode;
import http.constants.HttpVersion;

/**
 * Created by daleappleby on 4/10/15.
 */
interface IDefaultHeader {
    public HttpStatusCode getStatusCode();

    public void setStatusCode(HttpStatusCode statusCode);

    public HttpVersion getHttpVers();

    public void setHttpVers(HttpVersion httpVers);
}
