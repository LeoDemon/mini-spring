package tech.demonlee.minis.beans;

/**
 * @author Demon.Lee
 * @date 2023-05-17 13:25
 * @desc for managing Singleton Bean
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singleObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
