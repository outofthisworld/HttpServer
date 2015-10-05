package http;

/**
 * Created by daleappleby on 3/10/15.
 */
public enum HttpStatusCode {


    /************************************************************************************************
    * 1XX status codes. These codes are information status codes sent from the server to the client.*
    *************************************************************************************************/

    /*
    * This means that the server has received the request headers, and that the client should proceed to
    * send the request body (in the case of a request for which a body needs to be sent; for example, a POST request).
    * If the request body is large, sending it to a server when a request has already been rejected based upon inappropriate
    * headers is inefficient. To have a server check if the request could be accepted based on the request's headers alone,
    * a client must send Expect: 100-continue as a header in its initial request and check
    * if a 100 Continue status code is received in response before continuing (or receive 417 Expectation Failed and not continue).
    * */
    CONTINUE(100,"Continue"),

    /*
     * This means the requester has asked the server to switch protocols and the server is acknowledging that it will do so.
     */
    SWITCHING_PROTOCOLS(101,"Switching Protocols"),

    /* As a WebDAV request may contain many sub-requests involving file operations, it may take a long time to complete the request.
     * This code indicates that the server has received and is processing the request, but no response is available yet.[3]
     * This prevents the client from timing out and assuming the request was lost.
     */
    PROCESSING(102,"Processing"),

    /***********************************************************************
    * 2XX status codes. These status codes indicate a successful response. *
    ************************************************************************/

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
    ACCEPTED(202,"Accepted"),

    /*
    * The request has been fulfilled and resulted in a new resource being created.
    */
    CREATED(201,"Created"),

    /*
    * The server successfully processed the request, but is not returning any content.
    */
    NO_CONTENT(204,"No Content"),

    /*
    * The server successfully processed the request, but is not returning any content. Unlike a 204 response,
    * this response requires that the requester reset the document view.
    */
    RESET_CONTENT(205,"Reset Content"),

    /*
    * The server is delivering only part of the resource (byte serving) due to a range header sent by the client.
    * The range header is used by HTTP clients to enable resuming of interrupted downloads, or split a download into
    * multiple simultaneous streams.
    * */
    PARTIAL_CONTENT(205,"Partial Content"),

    /************************************************************************
     * 3XX status codes. Redirection status codes.                          *
     ************************************************************************/


    MOVED_PERMANENTLY(302,"Moved Permanently"),

    FOUND(302,"Found"),

    SEE_OTHER(303,"See other"),

    NOT_MODIFIED(304,"Not modified"),

    USE_PROXY(305,"Use Proxy"),

    SWITCH_PROXY(306,"Switch Proxy"),

    TEMP_REDIRECT(307,"Temporarily Redirect"),

    PERM_REDIRECT(308,"Permanent Redirect"),


    /************************************************************************
     * 4XX status codes. Client Error status codes                          *
     ************************************************************************/

    BAD_REQUEST(400,"Bad Request"),

    UNAUTHORIZED(401,"Unauthorized"),

    PAYMENT_REQUIRED(402,"Payment Required"),

    FORBIDDEN(403,"Forbidden"),

    NOT_FOUND(404, "Not Found"),

    METHOD_NOT_ALLOWED(405,"Method Not Allowed"),

    NOT_ACCEPTABLE(406,"Not Acceptable"),

    PROXY_AUTH_REQUIRED(407,"Proxy Authentication Required"),

    REQUEST_TIMEDOUT(408,"Request Timed Out"),

    CONFLICT(409,"Conflict"),

    GONE(410, "Gone"),

    LENGTH_REQUIRED(411,"Length Required"),

    PRECONDITION_FAILED(412,"Precodition Failed"),

    PAYLOAD_TO_LARGE(413,"Payload To Large"),

    REQUEST_URI_TO_LONG(414,"Request-URI To Long"),

    UNSUPPORTED_MEDIA_TYPE(415,"Unsupported Media Type"),

    UNSUPPORTED_REQUEST_RANGE(416,"Requested Range Not Satisfiable"),

    EXPECTATION_FAILED(417,"Expectation Failed");







    public int code;
    public String partName;

   HttpStatusCode(int status,String partName){
       this.code = status;
       this.partName = partName;
   }
}
