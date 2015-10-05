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
    private static final int PEEK_SEPERATOR = 3;
    private static final int PEEK_ERROR = 1;
    private int $state;

    public HttpHeader decode(final InputStream in,final Charset encoding) throws IOException {
        final CharBuffer chrBuf = CharBuffer.allocate(Configuration.MAX_HEADER_SIZE);
        bufRdr = new CharBufReader(chrBuf);
        readStream(in, chrBuf, encoding);

        bufRdr.markPosition();

        for(int i = 0; i < 2;i++) {
            int mark = bufRdr.getMarkPosition();
            int idx =  bufRdr.peekSeperator(HttpConstants.SPACE_CHAR, true);
            checkErrorState(idx);
            switch(i) {
                case 0: httpHeader.putHeader(
                        HttpHeaderConstants.METHOD.name, bufRdr.extract(mark, idx).get()
                );
                    break;
                case 1: httpHeader.putHeader(
                        HttpHeaderConstants.RESOURCE.name, bufRdr.extract(mark, idx).get()
                );
                    break;
            }
            bufRdr.markPosition();
        }

        checkErrorState(bufRdr.peekSeperator(HttpConstants.NEWLINE_CHAR, false));

        int idx = 0;
        for(; bufRdr.hasNext();){
            char c =  (char) bufRdr.readNext();
            try {
                System.out.println("Currently at: " +chrBuf.position());
                String key = bufRdr.extract(idx = bufRdr.peekSeperator(HttpConstants.COLON_CHAR, false)).get();
                System.out.println("Attempting to move to :" + idx);
                System.out.println("Now at " + chrBuf.position());

                if(bufRdr.peekSeperator(HttpConstants.NEWLINE_CHAR,true) != -1){

                }
                String value = bufRdr.extract(idx = bufRdr.peekSeperator(HttpConstants.NEWLINE_CHAR,false)).get();
                System.out.println("Attempting to move to :" + idx);
                System.out.println("Now at " + chrBuf.position());
                System.out.println(key);
                System.out.println(value);
                System.out.println();
            } catch (NoSuchElementException e) {
                System.out.println("Error" + idx);
            }
        }
        return httpHeader;
    }


    public void checkErrorState(int idx){
        if(idx == -1){
            $state = PEEK_ERROR;
        }
    }

    private void readStream(final InputStream in,final CharBuffer chrBuf,final Charset encoding) throws IOException {
        new BufferedReader(new InputStreamReader(in, encoding)).read(chrBuf);
        chrBuf.flip();
    }
}
