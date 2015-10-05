package http.header;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by daleappleby on 3/10/15.
 */
public abstract class AbstractHeader {
    private final HashMap<String,String> headerTags = new HashMap<>();



    public void forEachHeaderTag(BiConsumer<Iterator<Map.Entry<String,String>>,Map.Entry<String,String>> biConsumer){
        for(Iterator<Map.Entry<String,String>> it = headerTags.entrySet().iterator();it.hasNext();){
            Map.Entry headerTag = it.next();
            biConsumer.accept(it,headerTag);
        }
    }

    public String getHeader(String headerTag){
        return headerTags.get(headerTag);
    }

    public void putHeader(String headerTag,String headerValue){
        headerTags.put(headerTag,headerValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractHeader)) return false;

        AbstractHeader that = (AbstractHeader) o;

        if (headerTags != null ? !headerTags.equals(that.headerTags) : that.headerTags != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return headerTags != null ? headerTags.hashCode() : 0;
    }
}
