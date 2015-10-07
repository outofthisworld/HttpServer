package http.encode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.*;

/**
 * Created by daleappleby on 7/10/15.
 */
public final class FileCompressions {

    private FileCompressions(){}


    public static byte[] inflate(byte[] bytes) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(bytes,0,bytes.length);
        inflater.inflate(bytes);
        inflater.end();
        return bytes;
    }

    public static byte[] deflate(File file,int compressionLevel,int strategy) throws IOException {
        Deflater deflater = new Deflater();
        byte[] buffer = Files.readAllBytes(Paths.get(file.getPath()));
        deflater.setLevel(compressionLevel);
        deflater.setInput(buffer);
        deflater.setStrategy(strategy);
        byte[] deflated = new byte[(int)file.length()];
        deflater.deflate(deflated,0,deflated.length);
        deflater.finish();
        deflater.end();
        return deflated;
    }
    
    public static byte[] deGZIP(byte[] bytes) throws IOException {
        byte[] buffer = new byte[8000];
        GZIPInputStream streamIn = new GZIPInputStream(new ByteArrayInputStream(bytes));
        streamIn.read(buffer,0,bytes.length);
        streamIn.close();
        return buffer;
    }

    public static byte[] deGZIP(byte[] bytes,int length) throws IOException {
        byte[] buffer = new byte[length];
        GZIPInputStream streamIn = new GZIPInputStream(new ByteArrayInputStream(bytes));
        streamIn.read(buffer,0,length);
        streamIn.close();
        return buffer;
    }

    public static byte[] GZIP(String path) throws IOException {
        return GZIP(new File(path));
    }

    public static byte[] GZIP(Path path) throws IOException {
        return GZIP(path.toFile());
    }

    public static byte[] GZIP(File file) throws IOException {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)){
            byte[] buffer = Files.readAllBytes(Paths.get(file.getPath()));
            gzipOutputStream.write(buffer,0,buffer.length);
            gzipOutputStream.finish();
            gzipOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }
}
