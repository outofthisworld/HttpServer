package http.header;

/**
 * Created by daleappleby on 7/10/15.
 */
public class HttpHeaderFactory implements IHttpHeaderFactory {
    @Override
    public <T extends IDefaultHeader> T createHeader(HeaderType headerType) {
        switch (headerType){
            case DEFAULT_HEADER:
                return (T) new HttpHeader();
            case MINIMAL_HEADER:
                return (T) new MinimalHttpHeader();
            case FULL_HEADER:
                return (T) new FullHttpHeader();
        }
        return null;
    }
}
