package http.header;

/**
 * Created by daleappleby on 4/10/15.
 */
public interface IDefaultHeaderBuilder {
    /**
     * Gets header builder.
     *
     * @return the header builder
     */
    public <T extends HttpHeader.HttpHeaderBuilder> T getHeaderBuilder();
}
