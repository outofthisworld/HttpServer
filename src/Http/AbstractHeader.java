package http;

import java.util.HashMap;

/**
 * Created by daleappleby on 3/10/15.
 */
public class AbstractHeader {
    private final HashMap<String,String> headers = new HashMap<>();




    public String getHeader(String header){
        return headers.get(header);
    }
}
