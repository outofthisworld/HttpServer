package http.constants;

/**
 * Created by daleappleby on 3/10/15.
 */
public final class HttpConstants {
    /**
     * The constant BLANK.
     */
    public static final String BLANK = "";
    public static final char BLANK_CHAR= '\0';
    /**
     * The constant COMMA.
     */
    public static final String COMMA = ",";
    public static final char  COMMA_CHAR = ',';
    /**
     * The constant SPACE.
     */
    public static final String SPACE = " ";
    public static final char   SPACE_CHAR = ' ';
    /**
     * The constant COLON.
     */
    public static final String COLON = ":";
    public static final char COLON_CHAR = ':';
    /**
     * The constant SLASH.
     */
    public static final String SLASH = "/";
    public static final char SLASH_CHAR = '/';

    /**
     * The constant NEWLINE.
     *
     * HTTP/1.1 defines the sequence CR LF as the end-of-line marker for all
     * protocol elements except the entity-body (see appendix 19.3 for
     * tolerant applications). The end-of-line marker within an entity-body
     * is defined by its associated media type, as described in section 3.7.
     */
    public static final String NEWLINE = "\r\n";
    public static final char NEWLINE_CHAR = '\n';

    private HttpConstants(){}
}
