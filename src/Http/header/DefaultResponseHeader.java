package http.header;

import java.util.Date;

/**
 * Created by daleappleby on 4/10/15.
 */
public class DefaultResponseHeader extends Header {
    private Date date;
    private String contentType;
    private String server;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
