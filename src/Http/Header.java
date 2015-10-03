package Http;

import utils.Delimiters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

/**
 * Created by daleappleby on 3/10/15.
 */
public class Header {
    private static final Logger logger = Logger.getLogger(Header.class.getName());
    private HashMap<String,String> headers = new HashMap<>();
    private final HeaderReader headerReader;
    private final String header;
    private boolean isValidHeader = true;

    public Header(String header){
        this.header = header;
        headerReader = new HeaderReader(header);
        readHeader();
    }

    private final void readHeader(){
        try {
            String[] identity = headerReader.getAndSplit(Delimiters.REGEX_SPACE);
            headers.put("method",identity[0]);
            headers.put("resource",identity[1]);
            headers.put("httpVers",identity[2]);
        }catch (IndexOutOfBoundsException e){
            isValidHeader = false;
            return;
        }
        while(headerReader.hasNextLine()){
            String[] headerContents = headerReader.getAndSplit(Delimiters.COLON);
            if(headerContents.length  >=2){
                headers.put(headerContents[0], String.join(Delimiters.COLON,
                        Arrays.copyOfRange(headerContents, 1, headerContents.length)));
            }
        }
    }

    public void forEachHeader(BiConsumer<String,String> consumer){
        headers.forEach((k,v)->consumer.accept(k,v));
    }

    public HeaderReader getHeaderReader() {
        return headerReader;
    }

    public String getHeader() {
        return header;
    }

    public boolean isValidHeader() {
        return isValidHeader;
    }

    public void putHeader(String key,String val){
        headers.put(key,val);
    }

    public String getHeader(String header){
        return headers.get(header);
    }
}
