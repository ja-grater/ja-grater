package cn.enaiun.ja.grater.plugin;

import cn.enaiun.ja.grater.JarFileClassLoader;
import cn.enaiun.ja.grater.util.IOUtil;
import cn.enaiun.ja.grater.util.JarFileUtil;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
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
        List<JarFile> jarFiles = JarFileUtil.walkTree(dir);
        JarFileUtil.walkTree(dir).forEach(inst::appendToBootstrapClassLoaderSearch);
        JarFileClassLoader jarFileClassLoader = new JarFileClassLoader(jarFiles);
        try {
            for (JarFile jarFile : jarFiles) {
                JarEntry jarEntry = jarFile.getJarEntry("META-INF/services/" + PluginInitialize.class.getName());
                if (jarEntry != null) {
                    String service = new String(IOUtil.read(jarFile.getInputStream(jarEntry)), StandardCharsets.UTF_8);
                    try {
                        pluginInitializes.add((PluginInitialize) Class.forName(service, false, jarFileClassLoader).getConstructor().newInstance());
                    } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
