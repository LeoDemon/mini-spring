package tech.demonlee.minis.core.env;

/**
 * @author Demon.Lee
 * @date 2023-12-22 08:36
 */
public interface Environment extends PropertyResolver {

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);
}