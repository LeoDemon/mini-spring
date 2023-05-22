package tech.demonlee.minis.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Demon.Lee
 * @date 2023-05-10 23:34
 * @desc 1、让 {@link SimpleBeanFactory} 继承 {@link DefaultSingletonBeanRegistry}，
 * 从而保证通过 {@link SimpleBeanFactory} 创建的 Bean 都是单例的；
 * 2、BeanFactory 是工厂，SingletonBeanRegistry 是仓库，角色分离，前者负责 Bean 的获取，后者负责 Bean 的存储。
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);

    public SimpleBeanFactory() {
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        if (Objects.nonNull(singleton)) {
            return singleton;
        }

        BeanDefinition beanDefinition = beanDefinitions.get(beanName);
        singleton = this.createBean(beanDefinition);
        if (Objects.isNull(singleton)) {
            throw new RuntimeException("no such bean for beanDefinition: " + beanDefinition.getId());
        }
        this.registerSingleton(beanName, singleton);
        if (Objects.nonNull(beanDefinition.getInitMethodName())) {
            // Todo: init method
            System.out.println("invoke init method...");
        }

        return singleton;
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
        return Optional.ofNullable(beanDefinition).map(BeanDefinition::isSingleton).orElse(false);
    }

    @Override
    public boolean isPrototype(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
        return Optional.ofNullable(beanDefinition).map(BeanDefinition::isPrototype).orElse(false);
    }

    @Override
    public Class<?> getType(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
        return Optional.ofNullable(beanDefinition).map(BeanDefinition::getClass).orElse(null);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        if (Objects.isNull(beanDefinition)) {
            System.out.println("registerBeanDefinition error for null");
            return;
        }
        this.beanDefinitions.put(name, beanDefinition);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                System.out.println("init Bean " + name + " directly failed");
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitions.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitions.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitions.containsKey(name);
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz;
        Object obj;
        Constructor<?> con;

        ArgumentValues.Params argvParams = null;
        ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
        if (Objects.nonNull(argumentValues)) {
            argvParams = argumentValues.getParams();
        }
        try {
            clz = Class.forName(beanDefinition.getClassName());
            if (Objects.nonNull(argvParams)) {
                con = clz.getConstructor(argvParams.getTypes());
                obj = con.newInstance(argvParams.getValues());
            } else {
                obj = clz.getDeclaredConstructor().newInstance();
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        PropertyValues.Params propertyParams = null;
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (Objects.nonNull(propertyValues)) {
            propertyParams = propertyValues.getParams();
        }
        if (Objects.nonNull(propertyParams)) {
            int len = propertyValues.size();
            for (int i = 0; i < len; i++) {
                String methodName = propertyParams.getMethods()[i];
                Class<?>[] types = new Class<?>[]{propertyParams.getTypes()[i]};
                Object[] values = new Object[]{propertyParams.getValues()[i]};
                try {
                    Method method = clz.getMethod(methodName, types);
                    method.invoke(obj, values);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return obj;
    }
}
