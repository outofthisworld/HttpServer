package http.header;

import http.constants.HttpStatusCode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by daleappleby on 6/10/15.
 */
public interface HeaderDecoder {
    public abstract <T extends AbstractHeader> HttpStatusCode decode(T httpHeader, final InputStream in, final Charset encoding, boolean lenient) throws IOException;
}
