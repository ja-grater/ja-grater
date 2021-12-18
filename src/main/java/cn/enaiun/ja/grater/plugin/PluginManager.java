package cn.enaiun.ja.grater.plugin;

import cn.enaiun.ja.grater.JarFileClassLoader;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author Enaium
 */
public class PluginManager {

    private final Set<PluginInitialize> pluginInitializes = new HashSet<>();

    public Set<PluginInitialize> getPluginInitializes() {
        return pluginInitializes;
    }

    public void load(String dir, Instrumentation inst) {
        try {
            Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toFile().getName().endsWith(".jar")) {
                        JarFile jarFile = new JarFile(file.toFile());
                        Manifest manifest = jarFile.getManifest();
                        String value = manifest.getMainAttributes().getValue("ja-grater-plugin");
                        if (value != null) {
                            try {
                                JarFileClassLoader jarFileClassLoader = new JarFileClassLoader(jarFile);
                                inst.appendToBootstrapClassLoaderSearch(jarFile);
                                Class<?> klass = Class.forName(value, false, jarFileClassLoader);
                                pluginInitializes.add((PluginInitialize) klass.getConstructor().newInstance());
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
