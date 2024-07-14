package tech.demonlee.minis.beans.factory.support;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.config.AbstractAutowireCapableBeanFactory;
import tech.demonlee.minis.beans.factory.config.BeanDefinition;
import tech.demonlee.minis.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-12-14 09:38
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        if (Objects.isNull(type)) {
            return new String[0];
        }
        return beanDefinitionNames.stream()
                .filter(beanName -> matchBeanType(type, beanName))
                .toList()
                .toArray(new String[0]);
    }

    private boolean matchBeanType(Class<?> type, String beanName) {
        if (!this.containsBeanDefinition(beanName)) {
            return false;
        }
        BeanDefinition bd = this.getBeanDefinition(beanName);
        return type.isAssignableFrom(bd.getClass());
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        String[] beanNames = getBeanNamesForType(type);
        if (beanNames.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            Object beanInstance = getBean(beanName);
            result.put(beanName, (T) beanInstance);
        }
        return result;
    }
}
