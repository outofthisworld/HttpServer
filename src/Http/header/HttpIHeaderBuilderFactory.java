package http.header;

/**
 * Created by daleappleby on 4/10/15.
 */
public class HttpIHeaderBuilderFactory implements IHeaderFactory {
    private static final HttpIHeaderBuilderFactory httpHeaderBuilderFactory = new HttpIHeaderBuilderFactory();

    private HttpIHeaderBuilderFactory(){}

    public static HttpIHeaderBuilderFactory getDefault(){
        return httpHeaderBuilderFactory;
    }

    @Override
    public <T extends DefaultHttpHeaderBuilder, U extends HeaderI> T getHeaderBuilder(U header) {
        if(header instanceof IDefaultHeader) return (T) new DefaultHttpHeaderBuilder(header);
        return null;
    }
}
