package tech.demonlee.minis.beans.factory.config;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.BeanFactory;

/**
 * @author Demon.Lee
 * @date 2023-08-16 07:03
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeansException;
}
