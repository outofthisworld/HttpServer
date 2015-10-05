package http.header;

import http.constants.HttpHeaderConstants;
import http.constants.HttpStatusCode;
import http.constants.HttpVersion;
import http.constants.MimeTypes;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by daleappleby on 4/10/15.
 */
public class IncomingHttpResponseHeader extends IncomingHttpHeader {
    private Date date;
    private String contentType;

    public IncomingHttpResponseHeader(){}

    public IncomingHttpResponseHeader(HttpVersion vers, HttpStatusCode statusCode) {
        super(vers, statusCode);
    }

    public IncomingHttpResponseHeader(HttpVersion vers, HttpStatusCode statusCode, Date date) {
        super(vers, statusCode);
        setDate(date);
    }

    public IncomingHttpResponseHeader(HttpVersion vers, HttpStatusCode statusCode, MimeTypes mimeTypes) {
        super(vers, statusCode);
        setContentType(mimeTypes);
    }

    public IncomingHttpResponseHeader(HttpVersion vers, HttpStatusCode statusCode, Date date, MimeTypes mimeTypes) {
        super(vers, statusCode);
        setDate(date);
        setContentType(mimeTypes);
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets content type.
     *
     * @param contentType the content type
     */
    public void setContentType(MimeTypes... contentType) {
        this.contentType = String.join(";", Arrays.copyOfRange(
                Stream.of(contentType).map(e -> e.mimeType).toArray(),0,contentType.length,String[].class)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncomingHttpResponseHeader)) return false;
        if (!super.equals(o)) return false;

        IncomingHttpResponseHeader that = (IncomingHttpResponseHeader) o;

        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DefaultHttpResponseHeader{");
        sb.append("date=").append(date);
        sb.append(", contentType='").append(contentType).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public StringBuilder encode() {
         return super.encode().append(HttpHeaderConstants.CONTENT_TYPE.name).
                 append(getContentType()).append(HttpHeaderConstants.DATE.name).append(getDate());
    }

    public class DefaultHttpResponseBuilder extends DefaultHttpHeaderBuilder {
        private IncomingHttpResponseHeader defaultHttpResponseHeader;

        public DefaultHttpResponseBuilder(IncomingHttpResponseHeader header) {
            super(header);
            this.defaultHttpResponseHeader = header;
        }

        public DefaultHttpResponseBuilder setDate(Date date) {
            defaultHttpResponseHeader.setDate(date);
            return this;
        }

        public DefaultHttpResponseBuilder setContentType(MimeTypes... mimeTypes) {
            defaultHttpResponseHeader.setContentType(mimeTypes);
            return this;
        }

        @Override
        public StringBuilder encode() {
            return defaultHttpResponseHeader.encode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DefaultHttpResponseBuilder)) return false;
            if (!super.equals(o)) return false;

            DefaultHttpResponseBuilder that = (DefaultHttpResponseBuilder) o;

            if (defaultHttpResponseHeader != null ? !defaultHttpResponseHeader.equals(that.defaultHttpResponseHeader) : that.defaultHttpResponseHeader != null)
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (defaultHttpResponseHeader != null ? defaultHttpResponseHeader.hashCode() : 0);
            return result;
        }
    }
}
