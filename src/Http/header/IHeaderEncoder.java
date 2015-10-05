package http.header;

/**
 * Created by daleappleby on 4/10/15.
 */
public interface IHeaderEncoder {
    /**
     * Encode string.
     *
     * @return the string
     */
    public StringBuilder encode();
}
