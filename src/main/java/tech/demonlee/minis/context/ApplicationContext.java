package tech.demonlee.minis.context;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.ListableBeanFactory;
import tech.demonlee.minis.beans.factory.config.BeanFactoryPostProcessor;
import tech.demonlee.minis.beans.factory.config.ConfigurableBeanFactory;
import tech.demonlee.minis.beans.factory.config.ConfigurableListableBeanFactory;
import tech.demonlee.minis.core.env.Environment;
import tech.demonlee.minis.core.env.EnvironmentCapable;

/**
 * @author Demon.Lee
 * @date 2024-07-11 13:23
 */
public interface ApplicationContext extends EnvironmentCapable,
                ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {

        String getApplicationName();

        long getStartupDate();

        ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

        void setEnvironment(Environment env);

        Environment getEnvironment();

        void addBeanFactoryPostProcessor(BeanFactoryPostProcessor processor);

        void refresh() throws BeansException, IllegalStateException;

        void close();

        boolean isActive();
}
