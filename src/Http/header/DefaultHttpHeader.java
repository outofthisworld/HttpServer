package http.header;

/**
 * Created by daleappleby on 4/10/15.
 */
public class DefaultHttpHeader extends AbstractHeader {

    @Override
    public <T extends DefaultHttpHeaderBuilder> T getHeaderBuilder() {
        return HttpHeaderBuilderFactory.getDefault().getHeaderBuilder(this);
    }
}
