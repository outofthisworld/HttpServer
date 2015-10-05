package http.header;

import http.constants.HttpConstants;
import http.constants.HttpHeaderConstants;
import utils.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;

/**
 * Created by daleappleby on 5/10/15.
 */
public class HttpHeaderDecoder {
    private final HttpHeader httpHeader = new HttpHeader();
    private CharBufReader bufRdr;
    private static final int PEEK_NEWLINE = 0;
    private static final int PEEK_ERROR = 1;
    private int $state;

    public HttpHeader decode(final InputStream in,final Charset encoding) throws IOException {
        final CharBuffer chrBuf = CharBuffer.allocate(Configuration.MAX_HEADER_SIZE);
        bufRdr = new CharBufReader(chrBuf);
        readStream(in, chrBuf, encoding);

        bufRdr.markPosition();

        for(int i = 0; i < 2;i++) {
            try {
                int mark = bufRdr.getMarkPosition();

                String _headerConst = i == 0 ? HttpHeaderConstants.METHOD.name:HttpHeaderConstants.RESOURCE.name;

                int idx = bufRdr.peekSeperator(HttpConstants.SPACE_CHAR, true, false);

                httpHeader.putHeader(_headerConst, bufRdr.extract(mark,idx).get());

                bufRdr.markPosition();

            }catch (NoSuchElementException e){
                $state = PEEK_ERROR;
            }
        }

        for(; bufRdr.hasNext();){
            decodeChar(bufRdr.readNext());


        }


        httpHeader.forEachHeaderTag((k,v)-> System.out.println(v.getKey() + " " + v.getValue()));
        return httpHeader;
    }



    private final void decodeChar(int character){
        if(character == HttpConstants.NEWLINE_CHAR){
            $state = PEEK_NEWLINE;
        }
    }

    private void readStream(final InputStream in,final CharBuffer chrBuf,Charset encoding) throws IOException {
        new BufferedReader(new InputStreamReader(in, encoding)).read(chrBuf);
        chrBuf.flip();
    }
}
