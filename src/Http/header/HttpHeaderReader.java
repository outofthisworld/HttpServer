package http.header;

import http.constants.HttpConstants;
import utils.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

/**
 * Created by daleappleby on 4/10/15.
 */
public final class HttpHeaderReader {

    private static final byte PEEK_SPACE = 0;
    private static final byte PEEK_NEWLINE = 1;
    private static final byte PEEK_COLON = 2;
    private static final byte PEEK_BODY = 3;
    private static final byte PEEK_HEAD = 4;

    private HttpHeader httpHeader = new HttpHeader();
    private CharBuffer chrBuf;
    private int STATE;
    private int line;

    public static HttpHeader parseHeader(CharBuffer chrBuf){
        return new HttpHeaderReader(chrBuf).parseHeader();
    }

    public static HttpHeader par

    private HttpHeaderReader(CharBuffer charBuffer){

    }

    public HttpHeader parseHeader(InputStream in) throws IOException {
        CharBuffer chrBuffer = chrBuf.allocate(Configuration.MAX_HEADER_SIZE);
        new BufferedReader(new InputStreamReader(in)).read(chrBuffer);
        return parseHeader(chrBuffer);
    }

    public HttpHeader parseHeader(String header){
        return parseHeader(CharBuffer.wrap(header));
    }

    public HttpHeader parseHeader(){
        try {
            if(in.read(chrBuf) != -1){
                System.out.println("Not -1");
            }

            chrBuf.flip();
            chrBuf.mark();
            extractHead();

           /* char idx;
            decodeChar(idx = chrBuf.get());


            while(chrBuf.position() < chrBuf.limit()-1){

                if(STATE == PEEK_HEAD) {
                    httpHeader.putHeader();
                }

            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpHeader;
    }

    private final void extractHead() {
        int spc = 0;
        for (char c = chrBuf.get(); STATE != PEEK_NEWLINE && chrBuf.hasRemaining(); c = chrBuf.get()){
            decodeChar(c);
            if(STATE == PEEK_HEAD) {
                System.out.println(chrBuf.subSequence(chrBuf.ma, chrBuf.position()).array());
            }
        }
    }

    private final void decodeChar(char chr){
        if(line == PEEK_SPACE && Character.isSpaceChar(chr)){
            STATE = PEEK_HEAD;
        }if(chr == HttpConstants.NEWLINE.charAt(0) && STATE ==  PEEK_NEWLINE){
            STATE = PEEK_BODY;
            line++;
        } else if(chr == HttpConstants.NEWLINE.charAt(0)) {
            STATE = PEEK_NEWLINE;
            line++;
        }else if(chr == ':') {
            STATE = PEEK_COLON;
        }
    }
}
