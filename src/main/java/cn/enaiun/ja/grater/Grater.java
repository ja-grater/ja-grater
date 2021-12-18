package cn.enaiun.ja.grater;

import cn.enaiun.ja.grater.transformer.Transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Enaium
 */
public class Grater implements ClassFileTransformer {

    private final Map<String, Transformer> transformerMap = new HashMap<>();

    private final Set<String> targets = new HashSet<>();

    public void addTransformer(Transformer transformer) {
        transformerMap.put(transformer.target(), transformer);
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        Transformer transformer = transformerMap.get(className);
        if (transformer != null) {
            try {
                return transformer.transform(loader, className, classfileBuffer);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }

    public Set<String> getTargets() {
        return targets;
    }
}
