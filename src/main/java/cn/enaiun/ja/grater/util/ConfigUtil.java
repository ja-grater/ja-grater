package cn.enaiun.ja.grater.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Enaium
 */
public class ConfigUtil {
    public static Properties getConfig() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Paths.get(System.getenv("ja-grater-config")).toUri().toURL().openStream();
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
