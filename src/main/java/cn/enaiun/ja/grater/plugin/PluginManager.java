package cn.enaiun.ja.grater.plugin;


import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.jar.JarFile;

/**
 * @author Enaium
 */
public class PluginManager {

    private final Set<PluginInitialize> pluginInitializes = new HashSet<>();

    public Set<PluginInitialize> getPluginInitializes() {
        return pluginInitializes;
    }

    public void load(String dir, Instrumentation inst) {

        Set<URL> urls = new HashSet<>();
        try {
            Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toFile().getName().endsWith(".jar")) {
                        JarFile jarFile = new JarFile(file.toFile());
                        inst.appendToBootstrapClassLoaderSearch(jarFile);
                        urls.add(file.toUri().toURL());
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[0]), Thread.currentThread().getContextClassLoader());
        ServiceLoader<PluginInitialize> pluginInitializes = ServiceLoader.load(PluginInitialize.class, urlClassLoader);
        pluginInitializes.forEach(getPluginInitializes()::add);
    }
}
