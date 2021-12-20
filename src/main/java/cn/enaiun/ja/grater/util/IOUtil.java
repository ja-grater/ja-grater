package cn.enaiun.ja.grater.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Enaium
 */
public class IOUtil {
    public static byte[] read(InputStream inputStream) throws IOException {
        byte[] bytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, bytesRead);
            }
            outputStream.flush();
            bytes = outputStream.toByteArray();
        }
        return bytes;
    }
}
