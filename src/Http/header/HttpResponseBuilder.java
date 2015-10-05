package http.header;

import http.constants.HttpConstants;
import http.constants.HttpMethods;
import http.constants.HttpStatusCode;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by daleappleby on 3/10/15.
 */
public class HttpResponseBuilder {
    private HttpStatusCode responseCode;
    private String httpVers;
    private Date date;
    private String contentType;
    private String allowMethods;
    private String body;
    private int contentLength;
    private String server;

    public HttpResponseBuilder(){}

    public HttpResponseBuilder setHttpVers(String vers){
        this.httpVers = vers.replaceAll(HttpConstants.NEWLINE, HttpConstants.BLANK);
        return this;
    }

    public HttpResponseBuilder setHttpResponseCode(HttpStatusCode httpStatusCode){
        this.responseCode = httpStatusCode;
        return this;
    }

    public HttpResponseBuilder setContentType(String contentType){
        this.contentType = contentType.replaceAll(HttpConstants.NEWLINE, HttpConstants.BLANK);
        return this;
    }

    public HttpResponseBuilder setAllow(HttpMethods method){
        allowMethods = method.name();
        return this;
    }

    public HttpResponseBuilder setAllow(HttpMethods[] methods){
        allowMethods = String.join(HttpConstants.COMMA,  Arrays.copyOfRange(Stream.of(methods).map(m->m.name().toString()).toArray(),0,methods.length,String[].class));
        return this;
    }

    public HttpResponseBuilder setConnection(String connection){
        return null;
    }

    public HttpResponseBuilder setCacheControl(String cacheControl){
        return null;
    }

    public HttpResponseBuilder setContentLength(int contentLength){
        this.contentLength = contentLength;
        return this;
    }

    public HttpResponseBuilder setDate(Date date){
        this.date = date;
        return this;
    }

    public HttpResponseBuilder setBody(String body){
        this.body = body;
        return this;
    }

    public HttpResponseBuilder setServer(String server){
        this.server = server;
        return this;
    }

    public String build(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(httpVers);
        stringBuilder.append(HttpConstants.SPACE);
        stringBuilder.append(responseCode.code);
        stringBuilder.append(HttpConstants.SPACE);
        stringBuilder.append(responseCode.name());
        stringBuilder.append(HttpConstants.NEWLINE);
        stringBuilder.append("Content-type:");
        stringBuilder.append(contentType);
        stringBuilder.append(HttpConstants.NEWLINE);
        stringBuilder.append("Content-length:");
        stringBuilder.append(contentLength);
        stringBuilder.append(HttpConstants.NEWLINE);
        stringBuilder.append("Allow:");
        stringBuilder.append(allowMethods);
        stringBuilder.append(HttpConstants.NEWLINE);
        stringBuilder.append("Server:");
        stringBuilder.append(server);
        stringBuilder.append(HttpConstants.NEWLINE);
        stringBuilder.append(HttpConstants.NEWLINE);
        stringBuilder.append(body);
        return stringBuilder.toString();
    }
}
