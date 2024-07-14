package tech.demonlee.minis.beans.factory;

import tech.demonlee.minis.beans.BeansException;

import java.util.Map;

/**
 * @author Demon.Lee
 * @date 2023-12-14 09:09
 * @desc treat the Factory's internally managed beans as a collection, get the number of beans, get the names of all
 * beans, get a list of beans by a type, and so on
 */
public interface ListableBeanFactory extends BeanFactory {

    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
