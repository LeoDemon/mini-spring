package tech.demonlee.minis.context;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.BeanFactory;
import tech.demonlee.minis.beans.factory.config.BeanDefinition;
import tech.demonlee.minis.beans.factory.support.SimpleBeanFactory;
import tech.demonlee.minis.beans.factory.xml.XmlBeanDefinitionReader;
import tech.demonlee.minis.core.ClassPathXmlResource;
import tech.demonlee.minis.core.Resource;

/**
 * @author Demon.Lee
 * @date 2023-04-26 09:09
 * @desc app service for assembling, here is dispatch center
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
