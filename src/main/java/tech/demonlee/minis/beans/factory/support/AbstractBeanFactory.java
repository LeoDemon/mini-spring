package tech.demonlee.minis.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.PropertyValues;
import tech.demonlee.minis.beans.factory.config.BeanDefinition;
import tech.demonlee.minis.beans.factory.config.ConfigurableBeanFactory;
import tech.demonlee.minis.beans.factory.config.ConstructorArgumentValues;

/**
 * @author Demon.Lee
 * @date 2023-05-10 23:34
 * @desc 1、让 {@link AbstractBeanFactory} 继承 {@link DefaultSingletonBeanRegistry}，
 * 从而保证通过 {@link AbstractBeanFactory} 创建的 Bean 都是单例的；
 * 2、BeanFactory 是工厂，SingletonBeanRegistry 是仓库，角色分离，前者负责 Bean 的获取，后者负责 Bean 的存储。
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory,
        BeanDefinitionRegistry {

    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    protected List<String> beanDefinitionNames = new ArrayList<>(64);
    private final Map<String, Object> earlySingletonObject = new HashMap<>(64);

    public AbstractBeanFactory() {
    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (Objects.isNull(beanDefinition) || beanDefinition.isLazyInit()) {
                System.out.println("lazy init for bean: " + beanName);
                return;
            }

            try {
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        if (Objects.nonNull(singleton)) {
            return singleton;
        }

        singleton = earlySingletonObject.get(beanName);
        if (Objects.nonNull(singleton)) {
            return singleton;
        }
        System.out.println("getBean for: " + beanName);

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        singleton = this.createBean(beanDefinition);
        if (Objects.isNull(singleton)) {
            throw new BeansException("no such bean for beanDefinition: " + beanDefinition.getId());
        }

        registerBean(beanName, singleton);

        // BeanPostProcessor
        applyBeanPostProcessorBeforeInitialization(singleton, beanName);

        invokeInitMethod(beanDefinition, singleton);

        applyBeanPostProcessorAfterInitialization(singleton, beanName);

        return singleton;
    }

    public void registerBean(String beanName, Object singleton) {
        this.registerSingleton(beanName, singleton);
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object bean) {
        if (beanDefinition.hasNoInitMethod()) {
            return;
        }
        Class<?> clazz = bean.getClass();
        Method initMethod;
        try {
            initMethod = clazz.getDeclaredMethod(beanDefinition.getInitMethodName());
            initMethod.setAccessible(true);
            initMethod.invoke(bean);
        } catch (Exception ex) {
            throw new RuntimeException("invoke init method " + beanDefinition.getInitMethodWithClass() +
                    " failed: " + ex.getClass().getName() + ", " + ex.getMessage());
        }
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        return Optional.ofNullable(beanDefinition).map(BeanDefinition::isSingleton).orElse(false);
    }

    @Override
    public boolean isPrototype(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        return Optional.ofNullable(beanDefinition).map(BeanDefinition::isPrototype).orElse(false);
    }

    @Override
    public Class<?> getType(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        return Optional.ofNullable(beanDefinition).map(BeanDefinition::getClass).orElse(null);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        if (Objects.isNull(beanDefinition)) {
            System.out.println("registerBeanDefinition error for null");
            return;
        }
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Object obj = doCreateBean(beanDefinition);
        this.earlySingletonObject.put(beanDefinition.getId(), obj);

        Class<?> clz;
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        handleProperties(beanDefinition, clz, obj);

        return obj;
    }

    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz;
        Object obj;
        Constructor<?> con;

        ConstructorArgumentValues.Params argvParams = null;
        ConstructorArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
        if (Objects.nonNull(argumentValues)) {
            argvParams = argumentValues.getParams();
        }
        try {
            clz = Class.forName(beanDefinition.getClassName());
            if (Objects.nonNull(argvParams)) {
                con = clz.getConstructor(argvParams.getTypes());
                obj = con.newInstance(argvParams.getValues());
            } else {
                obj = clz.getConstructor().newInstance();
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        PropertyValues.Params propertyParams;
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (Objects.isNull(propertyValues)) {
            return;
        }

        propertyParams = propertyValues.getParams();
        if (Objects.isNull(propertyParams)) {
            return;
        }

        int len = propertyValues.size();
        for (int i = 0; i < len; i++) {
            String methodName = propertyParams.getMethods()[i];
            boolean isRef = propertyParams.getIsRefs()[i];
            Class<?>[] types = new Class<?>[]{propertyParams.getTypes()[i]};
            Object[] values = new Object[]{propertyParams.getValues()[i]};
            if (isRef) {
                Object refBean;
                try {
                    refBean = getBean(values[0].toString());
                } catch (BeansException e) {
                    throw new RuntimeException(e);
                }
                values[0] = refBean;
            }
            try {
                Method method = clz.getMethod(methodName, types);
                method.invoke(obj, values);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public abstract Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeansException;

    public abstract Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeansException;
}
