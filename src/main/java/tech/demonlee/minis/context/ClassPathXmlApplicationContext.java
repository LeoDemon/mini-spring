package tech.demonlee.minis.context;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.BeanFactory;
import tech.demonlee.minis.beans.factory.support.SimpleBeanFactory;
import tech.demonlee.minis.beans.factory.xml.XmlBeanDefinitionReader;
import tech.demonlee.minis.core.ClassPathXmlResource;
import tech.demonlee.minis.core.Resource;

/**
 * @author Demon.Lee
 * @date 2023-04-26 09:09
 * @desc app service for assembling, here is dispatch center
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    // 引入 BeanFactory 来获得对应的能力，而不是直接实现对应的方法
    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        // 从外部加载资源文件
        Resource resource = new ClassPathXmlResource(fileName);

        // 构造默认的 BeanFactory 核心类：获取 Bean 和 BeanDefinition
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();

        // 将 BeanDefinition 注册到 BeanFactory
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        this.beanFactory = beanFactory;

        if (isRefresh) {
            beanFactory.refresh();
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    @Override
    public Boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return this.beanFactory.isSingleton(beanName);
    }

    @Override
    public boolean isPrototype(String beanName) {
        return this.beanFactory.isPrototype(beanName);
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanFactory.getType(beanName);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
    }
}
