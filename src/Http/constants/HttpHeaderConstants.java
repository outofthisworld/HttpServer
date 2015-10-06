package http.constants;

/**
 * Created by daleappleby on 4/10/15.
 */
public enum HttpHeaderConstants {
   CONTENT_TYPE("Content-type"),
   CONTENT_LENGTH("Content-length"),
   ALLOW("Allow"),
   SERVER("Server"),
   DATE("Date"),
   METHOD("Method"),
   RESOURCE("Resource"),
   HTTP_VERS("Http-vers");

    public String name;
    HttpHeaderConstants(String name){
        this.name = name;
    }
}
