package http.header;

/**
 * Created by daleappleby on 5/10/15.
 */
public interface IHttpHeaderReader {
    public static final byte PEEK_SPACE = 0;
    public static final byte PEEK_NEWLINE = 1;
    public static final byte PEEK_SEPERATOR = 2;
    public static final byte PEEK_HEAD = 5;
    public static final byte PEEK_NONE = 6;

    public int state();
    public void proceed();
    public boolean hasNext();
    public void extract();
    public void peekInterest();
    public void reset();
    public void mark();
    public void setSeperator(char character);
    public void resetIndexCharCount();
}
