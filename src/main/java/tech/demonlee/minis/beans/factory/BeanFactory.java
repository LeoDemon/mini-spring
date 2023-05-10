package tech.demonlee.minis.beans.factory;

import tech.demonlee.minis.beans.factory.config.BeanDefinition;
import tech.demonlee.minis.beans.BeansException;

/**
 * @author Demon.Lee
 * @date 2023-05-10 23:11
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);
}
