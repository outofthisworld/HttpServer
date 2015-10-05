package http.header;

import java.util.Date;

/**
 * Created by daleappleby on 4/10/15.
 */
public class IDefaultResponseHeader extends HeaderI {
    private Date date;
    private String contentType;

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
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IDefaultResponseHeader)) return false;
        if (!super.equals(o)) return false;

        IDefaultResponseHeader that = (IDefaultResponseHeader) o;

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
}
