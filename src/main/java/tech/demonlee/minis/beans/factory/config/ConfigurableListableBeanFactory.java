package tech.demonlee.minis.beans.factory.config;

import tech.demonlee.minis.beans.factory.ListableBeanFactory;

/**
 * @author Demon.Lee
 * @date 2023-12-14 09:28
 * @desc for integrating multiple BeanFactory interfaces
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory,
        AutowireCapableBeanFactory, ConfigurableBeanFactory {
}
