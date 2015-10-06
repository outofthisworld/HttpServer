package http.header;

import http.constants.HttpConstants;
import http.constants.HttpHeaderConstants;
import http.constants.HttpStatusCode;
import utils.CharBufReader;
import utils.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import static http.constants.HttpConstants.NEWLINE_CHAR;
import static http.constants.HttpConstants.SPACE_CHAR;
import static http.constants.HttpHeaderConstants.HTTP_VERS;
import static http.constants.HttpHeaderConstants.METHOD;
import static http.constants.HttpHeaderConstants.RESOURCE;
import static http.constants.HttpStatusCode.BAD_REQUEST;

/**
 * Created by daleappleby on 5/10/15.
 */
public class HttpHeaderDecoder implements HeaderDecoder {
    private CharBufReader bufRdr;
    private AbstractHeader httpHeader;
    private int $state;

    public <T extends AbstractHeader> int decode(T httpHeaderIn, final InputStream in, final Charset encoding, boolean lenient) throws IOException {
        final CharBuffer chrBuf = CharBuffer.allocate(Configuration.MAX_HEADER_SIZE);
        bufRdr = new CharBufReader(chrBuf);
        readStream(in, chrBuf, encoding);
        httpHeader = httpHeaderIn;



        long startTim = System.currentTimeMillis();
        String line = bufRdr.extract(bufRdr.getPosition(),bufRdr.peek(NEWLINE_CHAR,true));
        String[] s = line.split(HttpConstants.SPACE);
        if(s.length!=3) System.out.println("error");
        httpHeader.putHeader(
                METHOD.name, s[0]
        );
        httpHeader.putHeader(
                HttpHeaderConstants.RESOURCE.name, s[1]
        );
        httpHeader.putHeader(
                HttpHeaderConstants.HTTP_VERS.name, s[2]
        );
        System.out.println("second Time " +(System.currentTimeMillis() - startTim));
        System.out.println(line);

        System.out.println(bufRdr.getPosition());
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            int mark = bufRdr.getMarkPosition();
            int idx = bufRdr.peek(SPACE_CHAR, true);
            if (idx == -1) return BAD_REQUEST.code;
            extractTopPart(i, mark, idx);
            bufRdr.markPosition();
        }
        System.out.println("Time " +(System.currentTimeMillis() - startTime));
        System.out.println(httpHeader.getHeader(METHOD.name));
        System.out.println();
        System.out.println(httpHeader.getHeader(RESOURCE.name));
        System.out.println();
        System.out.println(httpHeader.getHeader(HTTP_VERS.name));
        System.out.println();

        bufRdr.peek(NEWLINE_CHAR, false);

        for (; bufRdr.hasNext(); ) {
            char c = (char) bufRdr.readNext();


        }
        return HttpStatusCode.ACCEPTED.code;
    }

    public void decodeChar(int chr) {

    }

    private final void extractTopPart(int i, int mark, int idx) {
        switch (i) {
            case 0:
                httpHeader.putHeader(
                        METHOD.name, bufRdr.extract(mark, idx)
                );
                break;
            case 1:
                httpHeader.putHeader(
                        HttpHeaderConstants.RESOURCE.name, bufRdr.extract(mark, idx)
                );
            case 2:
                httpHeader.putHeader(
                        HttpHeaderConstants.HTTP_VERS.name, bufRdr.extract(mark, idx)
                );
                break;
        }
    }

    private void readStream(final InputStream in, final CharBuffer chrBuf, final Charset encoding) throws IOException {
        new BufferedReader(new InputStreamReader(in, encoding)).read(chrBuf);
        chrBuf.flip();
    }
}
