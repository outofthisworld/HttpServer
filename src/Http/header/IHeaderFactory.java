package http.header;

/**
 * Created by daleappleby on 4/10/15.
 */
public interface IHeaderFactory {
    public <T extends DefaultHttpHeaderBuilder,U extends IDefaultHeader> T getHeaderBuilder(U header);
}
