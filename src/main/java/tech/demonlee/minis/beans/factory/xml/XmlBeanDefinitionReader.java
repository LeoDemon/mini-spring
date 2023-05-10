package tech.demonlee.minis.beans.factory.xml;

import org.dom4j.Element;
import tech.demonlee.minis.beans.factory.BeanFactory;
import tech.demonlee.minis.beans.factory.config.BeanDefinition;
import tech.demonlee.minis.core.Resource;

/**
 * @author Demon.Lee
 * @date 2023-05-10 23:27
 */
public class XmlBeanDefinitionReader {

    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
