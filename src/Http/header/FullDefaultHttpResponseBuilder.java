package http.header;

/**
 * Created by daleappleby on 4/10/15.
 */
class FullDefaultHttpResponseBuilder extends DefaultHttpResponseBuilder {
    private FullHttpResponseHeader header;

    public FullDefaultHttpResponseBuilder(FullHttpResponseHeader header) {
        super(header);
        this.header = header;
    }

    //Implement this
    @Override
    public String encode() {
        return super.encode();
    }
}
