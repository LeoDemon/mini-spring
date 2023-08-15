package tech.demonlee.minis.beans.factory.support;

import tech.demonlee.minis.beans.factory.config.BeanDefinition;

/**
 * @author Demon.Lee
 * @date 2023-05-21 15:45
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);
}
