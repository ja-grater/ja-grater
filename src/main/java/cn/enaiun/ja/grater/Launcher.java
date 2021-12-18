package cn.enaiun.ja.grater;

import cn.enaiun.ja.grater.plugin.PluginInitialize;
import cn.enaiun.ja.grater.plugin.PluginManager;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.jar.JarFile;

/**
 * @author Enaium
 */
public class Launcher {

    public static void main(String[] args) {
        welcome();
    }

    public static void premain(String args, Instrumentation inst) throws IOException {
        welcome();
        inst.appendToBootstrapClassLoaderSearch(new JarFile(Launcher.class.getProtectionDomain().getCodeSource().getLocation().getFile()));
        Grater grater = new Grater();
        PluginManager pluginManager = new PluginManager();
        //Load plugin
        if (args != null) {
            pluginManager.load(args, inst);
            System.out.printf("Loaded %d Plugin\n", pluginManager.getPluginInitializes().size());
            for (PluginInitialize pluginInitialize : pluginManager.getPluginInitializes()) {
                System.out.printf("%s | %s | %s | %s\n", pluginInitialize.getName(), pluginInitialize.getAuthor(), pluginInitialize.getVersion(), pluginInitialize.getDescription());

                //Add transformer
                pluginInitialize.getTransformers().forEach(grater::addTransformer);
            }

            pluginManager.getPluginInitializes().forEach(PluginInitialize::initialize);
        }

        inst.addTransformer(grater, true);
        for (Class<?> klass : inst.getAllLoadedClasses()) {
            if (grater.getTargets().contains(klass.getName())) {
                try {
                    inst.retransformClasses(klass);
                } catch (UnmodifiableClassException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void welcome() {
        System.out.println("=======================================");
        System.out.println("               ja-grater               ");
        System.out.println("               By Enaium               ");
        System.out.println(" Homepage https://github.com/ja-grater ");
        System.out.println("=======================================");
    }
}
