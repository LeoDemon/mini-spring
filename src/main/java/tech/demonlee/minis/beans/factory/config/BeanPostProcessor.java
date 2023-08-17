package tech.demonlee.minis.beans.factory.config;

import tech.demonlee.minis.beans.BeansException;

/**
 * @author Demon.Lee
 * @date 2023-08-15 08:59
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
