package http.header;

import http.constants.HttpConstants;

import java.nio.CharBuffer;

/**
 * Created by daleappleby on 4/10/15.
 * @param <T>    the type parameter
 */
final class HttpHeaderReader<T extends AbstractHeader> implements IHttpHeaderReader {
    private final CharBuffer chrBuf;
    private final AbstractHeader httpHeader;
    private int state = -1;
    private int idxCharPos = -1;
    private int idxCharCount;
    private int upperBound;
    private int line;
    private int idxChar;
    private boolean peekedSep = false;

    public HttpHeaderReader(CharBuffer charBuffer,T httpHeader){
        this.chrBuf = charBuffer;
        this.httpHeader = httpHeader;
    }

    private final void extractHead(){

    }

    public void proceed(){
        decodeChar(chrBuf.get());
    }

    public boolean hasNext(){
        return chrBuf.hasRemaining();
    }

    public int state(){
      return state;
    }

    public void extract(){
        char[] value = new char[upperBound- idxCharPos];
        char[] key = new char[idxCharPos -chrBuf.position()];
        for(int i = 0,j = 0;chrBuf.position()!=upperBound;i++){
            if(chrBuf.position() < idxCharPos) {
                if(chrBuf.position() == idxCharPos) {
                    chrBuf.get();
                    continue;
                }
                key[i] = chrBuf.get();
            }else{
                value[j++] = chrBuf.get();
            }
        }
        httpHeader.putHeader(new String(key),new String(value));
    }

    public void peekInterest(){
        upperBound = chrBuf.position();
        chrBuf.reset();
    }


    public void mark(){
        chrBuf.mark();
    }

    public void setIndexChar(char charr){
        this.idxChar = charr;
    }

    public void resetIndexCharCount(){
        this.idxCharCount = 0;
    }

    public void decodeChar(char chr){
       if(chr == HttpConstants.NEWLINE.charAt(0) && line == PEEK_SPACE) {
           state = PEEK_HEAD;
           line++;
       }else if(chr == idxChar && !peekedSep) {
           peekedSep = true;
           idxCharCount++;
           idxCharPos = chrBuf.position();
           state = PEEK_SEPERATOR;
       }else if(chr == HttpConstants.NEWLINE.charAt(0)) {
            peekedSep = false;
            state = PEEK_NEWLINE;
           line++;
       }else {
           state =PEEK_NONE;
       }
    }

    public void reset(){
        state = -1;
        chrBuf.clear();
    }
}
