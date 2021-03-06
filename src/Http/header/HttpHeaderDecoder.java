package http.header;

import http.constants.*;
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
    private AbstractHeader httpHeader = new HttpHeader();

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

    public <T extends AbstractHeader> T decode(final InputStream in, final Charset encoding, boolean lenient) throws IOException {

        final CharBuffer chrBuf = CharBuffer.allocate(Configuration.MAX_HEADER_SIZE);

        bufRdr = new CharBufReader(chrBuf);

        readStream(in, chrBuf, encoding);

        if(!bufRdr.hasNextLine() ||
                !bufRdr.hasNextBefore(HttpConstants.SPACE_CHAR,HttpConstants.SPACE_CHAR,true))
            httpHeader.setStatusCode(HttpStatusCode.BAD_REQUEST);

        HttpMethods reqMethod;
        if((reqMethod = determineRequestMethod()) != null
                && checkValidPathStart(bufRdr.readNext())) {
            setRequestMethod(reqMethod);
            setResourcePath(bufRdr.extract(bufRdr.getPosition(),bufRdr.peek(HttpConstants.SPACE_CHAR,false)));
        }else{
            return (T) httpHeader.getHeaderBuilder().setStatusCode(HttpStatusCode.BAD_REQUEST).toHeader();
        }

        while(bufRdr.hasNext()){
            decodeChar(bufRdr.readNext());


        }


        return (T) httpHeader.getHeaderBuilder().setStatusCode(HttpStatusCode.ACCEPTED).toHeader();
    }

    private final void decodeChar(int chr) {
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

    private final HttpMethods determineRequestMethod(){
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
        bufRdr.peek(HttpConstants.SPACE_CHAR,false);
        return method;
    }

    public boolean checkValidPathStart(int c){
        return (char) c == HttpConstants.SLASH_CHAR?true:false;
    }

    private final void setResourcePath(String path){
        httpHeader.putHeader(HttpHeaderConstants.RESOURCE,path);
    }

    private final <T extends HttpVersion> void setHttpVersion(T httpVersion){
        httpHeader.putHeader(HttpHeaderConstants.HTTP_VERS,httpVersion.version);
        httpHeader.setHttpVers(httpVersion);
    }

    private final <T extends HttpMethods> void setRequestMethod(T method){
        httpHeader.putHeader(HttpHeaderConstants.METHOD,method.name());
        httpHeader.setRequestMethod(method);
    }

    private void readStream(final InputStream in, final CharBuffer chrBuf, final Charset encoding) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding))){
            br.read(chrBuf);
            chrBuf.flip();
        }
    }
}
