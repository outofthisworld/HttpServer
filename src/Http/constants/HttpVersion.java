package http.constants;

/**
 * Created by daleappleby on 4/10/15.
 */
public enum HttpVersion {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1");


    public String version;
    HttpVersion(String version){
        this.version = version;
    }
}
