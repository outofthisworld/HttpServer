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
     */
    public static final String NEWLINE = System.getProperty("line.separator");
    public static final char NEWLINE_CHAR = System.getProperty("line.separator").charAt(0);
    /**
     * The constant REGEX_SPACE.
     */
    public static final String REGEX_SPACE = "\\s";

    private HttpConstants(){}
}
