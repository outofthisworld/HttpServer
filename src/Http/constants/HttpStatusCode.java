package http.constants;

/**
 * Created by daleappleby on 3/10/15.
 */
public enum HttpStatusCode {


    /**
     * *********************************************************************************************
     * 1XX status codes. These codes are information status codes sent from the server to the client.*
     */

    /*
    * This means that the server has received the request headers, and that the client should proceed to
    * send the request body (in the case of a request for which a body needs to be sent; for example, a POST request).
    * If the request body is large, sending it to a server when a request has already been rejected based upon inappropriate
    * headers is inefficient. To have a server check if the request could be accepted based on the request's headers alone,
    * a client must send Expect: 100-continue as a header in its initial request and check
    * if a 100 Continue status code is received in response before continuing (or receive 417 Expectation Failed and not continue).
    * */
    CONTINUE(100, "Continue"),


    /*
     * This means the requester has asked the server to switch protocols and the server is acknowledging that it will do so.
     */
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),


    /* As a WebDAV request may contain many sub-requests involving file operations, it may take a long time to complete the request.
     * This code indicates that the server has received and is processing the request, but no response is available yet.[3]
     * This prevents the client from timing out and assuming the request was lost.
     */
    PROCESSING(102, "Processing"),


    /*
     * ********************************************************************
     * 2XX status codes. These status codes indicate a successful response. *
     */

    /*
    * Standard response for successful HTTP requests. The actual response will depend on the request method used.
    * In a GET request, the response will contain an entity corresponding to the requested resource.
    * In a POST request, the response will contain an entity describing or containing the result of the action.
    */
    OK(200, "Ok"),


    /*
        * The request has been accepted for processing, but the processing has not been completed.
        * The request might or might not eventually be acted upon, as it might be disallowed when processing actually takes place.
        */
    ACCEPTED(202, "Accepted"),


    /*
     * The request has been fulfilled and resulted in a new resource being created.
     */
    CREATED(201, "Created"),


    /*
     * The server successfully processed the request, but is not returning any content.
     */
    NO_CONTENT(204, "No Content"),


    /*
    * The server successfully processed the request, but is not returning any content. Unlike a 204 response,
    * this response requires that the requester reset the document view.
    */
    RESET_CONTENT(205, "Reset Content"),


    /*
    * The server is delivering only part of the resource (byte serving) due to a range header sent by the client.
    * The range header is used by HTTP clients to enable resuming of interrupted downloads, or split a download into
    * multiple simultaneous streams.
    * */
    PARTIAL_CONTENT(205, "Partial Content"),


    /**
     * *********************************************************************
     * 3XX status codes. Redirection status codes.                          *
     */


    MOVED_PERMANENTLY(302, "Moved Permanently"),


    /**
     * The FOUND.
     */
    FOUND(302, "Found"),


    /**
     * The SEE_OTHER.
     */
    SEE_OTHER(303, "See other"),


    /**
     * The NOT_MODIFIED.
     */
    NOT_MODIFIED(304, "Not modified"),


    /**
     * The USE_PROXY.
     */
    USE_PROXY(305, "Use Proxy"),


    /**
     * The SWITCH_PROXY.
     */
    SWITCH_PROXY(306, "Switch Proxy"),


    /**
     * The TEMP_REDIRECT.
     */
    TEMP_REDIRECT(307, "Temporarily Redirect"),


    /**
     * The PERM_REDIRECT.
     */
    PERM_REDIRECT(308, "Permanent Redirect"),


    /**
     * *********************************************************************
     * 4XX status codes. Client Error status codes                          *
     */

    BAD_REQUEST(400, "Bad Request"),


    /**
     * The UNAUTHORIZED.
     */
    UNAUTHORIZED(401, "Unauthorized"),


    /**
     * The PAYMENT_REQUIRED.
     */
    PAYMENT_REQUIRED(402, "Payment Required"),


    /**
     * The FORBIDDEN.
     */
    FORBIDDEN(403, "Forbidden"),


    /**
     * The NOT_FOUND.
     */
    NOT_FOUND(404, "Not Found"),


    /**
     * The METHOD_NOT_ALLOWED.
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),


    /**
     * The NOT_ACCEPTABLE.
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),


    /**
     * The PROXY_AUTH_REQUIRED.
     */
    PROXY_AUTH_REQUIRED(407, "Proxy Authentication Required"),


    /**
     * The REQUEST_TIMEDOUT.
     */
    REQUEST_TIMEDOUT(408, "Request Timed Out"),


    /**
     * The CONFLICT.
     */
    CONFLICT(409, "Conflict"),


    /**
     * The GONE.
     */
    GONE(410, "Gone"),


    /**
     * The LENGTH_REQUIRED.
     */
    LENGTH_REQUIRED(411, "Length Required"),


    /**
     * The PRECONDITION_FAILED.
     */
    PRECONDITION_FAILED(412, "Precodition Failed"),


    /**
     * The PAYLOAD_TO_LARGE.
     */
    PAYLOAD_TO_LARGE(413, "Payload To Large"),


    /**
     * The REQUEST_URI_TO_LONG.
     */
    REQUEST_URI_TO_LONG(414, "Request-URI To Long"),


    /**
     * The UNSUPPORTED_MEDIA_TYPE.
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),


    /**
     * The UNSUPPORTED_REQUEST_RANGE.
     */
    UNSUPPORTED_REQUEST_RANGE(416, "Requested Range Not Satisfiable"),


    /**
     * The EXPECTATION_FAILED.
     */
    EXPECTATION_FAILED(417, "Expectation Failed");


    /**
     * The Code.
     */
    public int code;
    /**
     * The Part name.
     */
    public String partName;

    /**
     * Instantiates a new Http status code.
     *
     * @param status   the status
     * @param partName the part name
     */
    HttpStatusCode(int status, String partName) {
        this.code = status;
        this.partName = partName;
    }
}
