package tech.demonlee.minis.beans.factory.config;

import tech.demonlee.minis.beans.factory.BeanFactory;

/**
 * @author Demon.Lee
 * @date 2023-12-14 09:16
 * @desc for maintaining dependencies between beans and supporting bean handlers
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);
}
