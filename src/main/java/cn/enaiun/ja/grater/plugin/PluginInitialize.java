package cn.enaiun.ja.grater.plugin;

import cn.enaiun.ja.grater.transformer.Transformer;

import java.util.List;
import java.util.Properties;

/**
 * @author Enaium
 * @since 1.0.0
 */
public interface PluginInitialize {

    /**
     * plugin initialization
     * @param properties agent config
     */
    default void initialize(Properties properties) {

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
