package http.header;

import http.constants.HttpConstants;
import http.constants.HttpHeaderConstants;
import utils.CharBufReader;
import utils.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by daleappleby on 5/10/15.
 */
public class HttpHeaderDecoder {
    private CharBufReader bufRdr;
    private int $state;

    public <T extends AbstractHeader> boolean decode(T httpHeader,final InputStream in,final Charset encoding,boolean lenient) throws IOException {
        final CharBuffer chrBuf = CharBuffer.allocate(Configuration.MAX_HEADER_SIZE);
        bufRdr = new CharBufReader(chrBuf);
        readStream(in, chrBuf, encoding);

        for(int i = 0; i < 2;i++) {
            int mark = bufRdr.getMarkPosition();
            int idx =  bufRdr.peek(HttpConstants.SPACE_CHAR, true);

            if(idx == -1) return false;

            switch(i) {
                case 0: httpHeader.putHeader(
                        HttpHeaderConstants.METHOD.name, bufRdr.extract(mark, idx)
                );
                    break;
                case 1: httpHeader.putHeader(
                        HttpHeaderConstants.RESOURCE.name, bufRdr.extract(mark, idx)
                );
                    break;
            }
            bufRdr.markPosition();
        }

        bufRdr.peek(HttpConstants.NEWLINE_CHAR, false);


        for(; bufRdr.hasNext();){
            char c =  (char) bufRdr.readNext();


        }
        return true;
    }

    public void decodeChar(int chr){

    }

    private void readStream(final InputStream in,final CharBuffer chrBuf,final Charset encoding) throws IOException {
        new BufferedReader(new InputStreamReader(in, encoding)).read(chrBuf);
        chrBuf.flip();
    }
}
