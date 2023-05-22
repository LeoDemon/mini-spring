package tech.demonlee.minis.beans;

/**
 * @author Demon.Lee
 * @date 2023-05-10 23:11
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    Boolean containsBean(String beanName);

    boolean isSingleton(String beanName);

    boolean isPrototype(String beanName);

    Class<?> getType(String beanName);
}
