package tech.demonlee.minis.beans.factory.config;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.BeanFactory;

/**
 * @author Demon.Lee
 * @date 2024-07-11 13:21
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
