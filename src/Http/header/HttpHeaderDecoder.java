package http.header;

import http.constants.HttpConstants;
import http.constants.HttpHeaderConstants;
import http.constants.HttpMethods;
import http.constants.HttpStatusCode;
import utils.CharBufReader;
import utils.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import static http.constants.HttpConstants.*;

/**
 * Created by daleappleby on 5/10/15.
 */
public class HttpHeaderDecoder implements HeaderDecoder {
    //The char buf reader.
    private CharBufReader bufRdr;

    //The http header
    private AbstractHeader httpHeader;

    //Decoder states
    private static final int PEEK_SPACE = 0;
    private static final int PEEK_NEWLINE = 1;
    private static final int PEEK_DELIMITER = 2;
    private static final int PEEK_ERROR = 3;

    //The current state
    private int $state;

    //The line NO.
    private int line;
    private int space;

    public <T extends AbstractHeader> HttpStatusCode decode(T httpHeaderIn, final InputStream in, final Charset encoding, boolean lenient) throws IOException {
        final CharBuffer chrBuf = CharBuffer.allocate(Configuration.MAX_HEADER_SIZE);
        bufRdr = new CharBufReader(chrBuf);
        readStream(in, chrBuf, encoding);
        httpHeader = httpHeaderIn;

        if(!bufRdr.hasNextBefore(HttpConstants.SPACE_CHAR,HttpConstants.SPACE_CHAR,true))
            return HttpStatusCode.BAD_REQUEST;

        HttpMethods reqMethod;
        if((reqMethod = determineRequestMethod()) != null) {
            httpHeader.putHeader(HttpHeaderConstants.METHOD.name,reqMethod.name());
        }else{
            return HttpStatusCode.BAD_REQUEST;
        }

        while(bufRdr.hasNext()){
            decodeChar(bufRdr.readNext());

            if($state == PEEK_NEWLINE){

            }



        }


        return HttpStatusCode.ACCEPTED;
    }

    public void decodeChar(int chr) {
        if(chr == SPACE_CHAR){
            $state = PEEK_SPACE;
            space++;
        }else if (chr == NEWLINE_CHAR){
            $state = PEEK_NEWLINE;
            line++;
        }else if (chr == COLON_CHAR){
            $state = PEEK_DELIMITER;
        }
    }

    private HttpMethods determineRequestMethod(){
        HttpMethods method = null;
        switch(bufRdr.readNext()){
            case 'G':
                method = HttpMethods.GET;
                break;
            case 'D':
                method = HttpMethods.DELETE;
                break;
            case 'P':
                if(bufRdr.readNext() == 'U')
                    method = HttpMethods.PUT;
                method = HttpMethods.POST;
                break;
        }
        bufRdr.peek(HttpConstants.NEWLINE_CHAR,false);
        return method;
    }
    
    private void readStream(final InputStream in, final CharBuffer chrBuf, final Charset encoding) throws IOException {
        new BufferedReader(new InputStreamReader(in, encoding)).read(chrBuf);
        chrBuf.flip();
    }
}
