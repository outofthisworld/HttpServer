package http.header;

/**
 * Created by daleappleby on 7/10/15.
 */
public interface IHttpHeaderFactory {
    public <T extends IDefaultHeader> T createHeader(HeaderType headerType);
}
