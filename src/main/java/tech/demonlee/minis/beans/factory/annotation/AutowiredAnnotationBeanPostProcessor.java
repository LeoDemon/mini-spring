package tech.demonlee.minis.beans.factory.annotation;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.BeanFactory;
import tech.demonlee.minis.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-08-15 09:06
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String className = clazz.getSimpleName();

        for (Field field : fields) {
            boolean isAutowired = field.isAnnotationPresent(Autowired.class);
            if (!isAutowired) {
                continue;
            }
            String fieldName = field.getName();
            System.out.println("insert bean for Autowire field: " + className + "." + fieldName);
            Object autowiredBean = beanFactory.getBean(fieldName);
            if (Objects.isNull(autowiredBean)) {
                throw new BeansException("No bean found for: " + className + "." + fieldName);
            }
            field.setAccessible(true);
            try {
                field.set(bean, autowiredBean);
            } catch (IllegalAccessException e) {
                throw new BeansException("Set bean for " + className + "." + fieldName + " failed: " + e.getMessage());
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
