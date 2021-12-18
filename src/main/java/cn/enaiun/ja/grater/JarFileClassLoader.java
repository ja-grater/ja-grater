package cn.enaiun.ja.grater;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Enaium
 */
public class JarFileClassLoader extends ClassLoader {

    private final JarFile jarFile;

    public JarFileClassLoader(JarFile jarFile) {
        this.jarFile = jarFile;
    }

    @Override
    protected Class<?> findClass(String name) {
        JarEntry jarEntry = jarFile.getJarEntry(name.replace(".", "/") + ".class");
        byte[] bytes = new byte[0];
        try {
            try (InputStream inputStream = jarFile.getInputStream(jarEntry); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                byte[] data = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                    outputStream.write(data, 0, bytesRead);
                }
                outputStream.flush();
                bytes = outputStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.defineClass(name, bytes, 0, bytes.length);
    }
}
