package cn.enaiun.ja.grater;

import cn.enaiun.ja.grater.util.IOUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Enaium
 */
public class JarFileClassLoader extends ClassLoader {

    private final List<JarFile> jarFiles;

    public JarFileClassLoader(List<JarFile> jarFiles) {
        this.jarFiles = jarFiles;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = new byte[0];
        for (JarFile jarFile : jarFiles) {
            JarEntry jarEntry = jarFile.getJarEntry(name.replace(".", "/") + ".class");
            if (jarEntry != null) {
                try {
                    bytes = IOUtil.read(jarFile.getInputStream(jarEntry));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return super.defineClass(name, bytes, 0, bytes.length);
    }

    public List<JarFile> getJarFiles() {
        return jarFiles;
    }
}
