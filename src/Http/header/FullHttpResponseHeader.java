package http.header;

import http.constants.HttpMethods;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by daleappleby on 4/10/15.
 */
public class FullHttpResponseHeader extends DefaultHttpResponseHeader {
    private String accept;


    public void setAccept(HttpMethods... accept) {
        this.accept = String.join(",", Arrays.copyOfRange(
                        Stream.of(accept).map(e -> e.name()).toArray(), 0, accept.length, String[].class)
        );
    }
}
