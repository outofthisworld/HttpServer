package http.header;

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
    private final HttpHeader httpHeader = new HttpHeader();

    public HttpHeader decode(InputStream in,Charset encoding) throws IOException {

        CharBuffer chrBuf = CharBuffer.allocate(Configuration.MAX_HEADER_SIZE);
        readStream(in,chrBuf,encoding);

        final IHttpHeaderReader reader = new HttpHeaderReader(chrBuf,httpHeader);

        reader.mark();

        for(;reader.hasNext();){

            if(reader.state()== HttpHeaderReader.PEEK_HEAD){
                reader.peekInterest();
                reader.mark();
            }else if(reader.state() == HttpHeaderReader.PEEK_SEPERATOR){
                clnIdx = chrBuf.position();
            }else if(reader.state() == HttpHeaderReader.PEEK_NEWLINE){
                reader.peekInterest();
                reader.extract();
                reader.mark();
            }
        }

        httpHeader.forEachHeaderTag((k,v)-> System.out.println(v.getKey() + " " + v.getValue()));

        reader.reset();
        return httpHeader;
    }

    private void readStream(InputStream in,CharBuffer chrBuf,Charset encoding) throws IOException {
        new BufferedReader(new InputStreamReader(in, encoding)).read(chrBuf);
        chrBuf.flip();
    }
}
