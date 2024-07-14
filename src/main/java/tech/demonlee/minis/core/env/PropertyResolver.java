package tech.demonlee.minis.core.env;

/**
 * @author Demon.Lee
 * @date 2023-12-22 08:37
 */
public interface PropertyResolver {

    boolean containsProperty(String key);

    String getProperty(String key);

    String getProperty(String key, String defaultProperty);

    <T> T getProperty(String key, Class<T> targetType);

    <T> T getProperty(String key, Class<T> targetType, T defaultProperty);

    <T> Class<T> getPropertyAsClass(String key, Class<T> targetType);

    String getRequiredProperty(String key) throws IllegalStateException;

    <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

    String resolvePlaceHolders(String text);

    String resolveRequiredPlaceHolders(String text) throws IllegalArgumentException;
}
