package cn.enaiun.ja.grater.plugin;

import cn.enaiun.ja.grater.transformer.Transformer;

import java.util.List;

/**
 * @author Enaium
 * @since 1.0.0
 */
public interface PluginInitialize {

    /**
     * plugin initialization
     */
    default void initialize() {

    }

    /**
     * @return plugin name
     */
    String getName();

    /**
     * @return plugin author
     */
    String getAuthor();

    /**
     * @return plugin version
     */
    String getVersion();

    /**
     * @return plugin description
     */
    String getDescription();

    /**
     * @return plugin transformers
     */
    List<Transformer> getTransformers();
}
