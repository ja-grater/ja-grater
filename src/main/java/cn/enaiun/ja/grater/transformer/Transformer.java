package cn.enaiun.ja.grater.transformer;

/**
 * @author Enaium
 * @since 1.0.0
 */
public interface Transformer {
    /**
     * Target class
     *
     * @return class owner
     * @since 1.0.0
     */
    String target();

    /**
     * Transform class
     *
     * @param loader class loader
     * @param name   class name
     * @param basic  class bytes
     * @return Transformed class bytes
     * @throws Throwable all throwable
     * @since 1.0.0
     */
    byte[] transform(ClassLoader loader, String name, byte[] basic) throws Throwable;
}
