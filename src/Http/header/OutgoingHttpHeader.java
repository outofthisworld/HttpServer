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
public class OutgoingHttpHeader extends HttpHeader {
    private Date date;
    private String contentType;

    public OutgoingHttpHeader() {
    }

    public OutgoingHttpHeader(HttpVersion vers) {
        super(vers);
    }

    public OutgoingHttpHeader(HttpStatusCode httpStatusCode) {
        super(httpStatusCode);
    }

    public OutgoingHttpHeader(HttpVersion vers, HttpStatusCode statusCode) {
        super(vers, statusCode);
    }

    public OutgoingHttpHeader(HttpVersion vers, HttpStatusCode statusCode, Date date) {
        super(vers, statusCode);
        setDate(date);
    }

    public OutgoingHttpHeader(HttpVersion vers, HttpStatusCode statusCode, MimeTypes mimeTypes) {
        super(vers, statusCode);
        setContentType(mimeTypes);
    }

    public OutgoingHttpHeader(HttpVersion vers, HttpStatusCode statusCode, Date date, MimeTypes mimeTypes) {
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
                        Stream.of(contentType).map(e -> e.mimeType).toArray(), 0, contentType.length, String[].class)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutgoingHttpHeader)) return false;
        if (!super.equals(o)) return false;

        OutgoingHttpHeader that = (OutgoingHttpHeader) o;

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

    @Override
    public HttpHeaderBuilder getHeaderBuilder() {
        return super.getHeaderBuilder();
    }

    public class DefaultHttpResponseBuilder extends HttpHeaderBuilder {

        /**
         * Instantiates a new Http header builder.
         * Note: this class takes in an instance of the outer class in so that it can be initiated
         * from outside this class.
         *
         * @param header the header
         */
        public DefaultHttpResponseBuilder(AbstractHeader header) {
            super(header);
        }

        public DefaultHttpResponseBuilder setDate(Date date) {
            OutgoingHttpHeader.this.setDate(date);
            return this;
        }

        public DefaultHttpResponseBuilder setContentType(MimeTypes... mimeTypes) {
            OutgoingHttpHeader.this.setContentType(mimeTypes);
            return this;
        }

        @Override
        public StringBuilder encode() {
            return OutgoingHttpHeader.this.encode();
        }
    }
}
