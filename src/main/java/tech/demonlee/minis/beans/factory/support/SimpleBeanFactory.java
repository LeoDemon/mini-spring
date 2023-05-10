package tech.demonlee.minis.beans.factory.support;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.BeanFactory;
import tech.demonlee.minis.beans.factory.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Demon.Lee
 * @date 2023-05-10 23:34
 */
public class SimpleBeanFactory implements BeanFactory {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    public SimpleBeanFactory() {
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = singletons.get(beanName);
        if (Objects.nonNull(singleton)) {
            return singleton;
        }
        int i = beanNames.indexOf(beanName);
        if (-1 == i) {
            throw new BeansException("no bean for name: " + beanName);
        }

        BeanDefinition beanDefinition = beanDefinitions.get(i);
        try {
            singleton = Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance();
            singletons.put(beanDefinition.getId(), singleton);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        if (Objects.isNull(beanDefinition)) {
            System.out.println("registerBeanDefinition error for null");
            return;
        }
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
