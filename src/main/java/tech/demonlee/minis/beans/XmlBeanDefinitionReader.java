package tech.demonlee.minis.beans;

import org.dom4j.Element;
import tech.demonlee.minis.core.Resource;

import java.util.List;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-05-10 23:27
 */
public class XmlBeanDefinitionReader {

    private static final String XML_CONF_BEAN_ID = "id";
    private static final String XML_CONF_BEAN_CLASS_NAME = "class";
    private static final String XML_CONF_BEAN_CONSTRUCTOR_ELEMENT = "constructor-arg";
    private static final String XML_CONF_BEAN_CONSTRUCTOR_TYPE = "type";
    private static final String XML_CONF_BEAN_CONSTRUCTOR_VALUE = "value";
    private static final String XML_CONF_BEAN_CONSTRUCTOR_NAME = "name";
    private static final String XML_CONF_BEAN_PROPERTY_ELEMENT = "property";
    private static final String XML_CONF_BEAN_PROPERTY_TYPE = "type";
    private static final String XML_CONF_BEAN_PROPERTY_VALUE = "value";
    private static final String XML_CONF_BEAN_PROPERTY_NAME = "name";

    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue(XML_CONF_BEAN_ID);
            String beanClassName = element.attributeValue(XML_CONF_BEAN_CLASS_NAME);
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            ArgumentValues argumentValues = getArgumentValues(element);
            beanDefinition.setConstructorArgumentValues(argumentValues);

            PropertyValues propertyValues = getPropertyValues(element);
            beanDefinition.setPropertyValues(propertyValues);

            this.simpleBeanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

    private ArgumentValues getArgumentValues(Element element) {
        ArgumentValues argumentValues = null;
        List<Element> argElements = element.elements(XML_CONF_BEAN_CONSTRUCTOR_ELEMENT);
        if (Objects.nonNull(argElements) && !argElements.isEmpty()) {
            argumentValues = new ArgumentValues();
            argElements.stream().map(this::getArgumentValue).forEach(argumentValues::addArgumentValue);
        }
        return argumentValues;
    }

    private ArgumentValue getArgumentValue(Element e) {
        String type = e.attributeValue(XML_CONF_BEAN_CONSTRUCTOR_TYPE);
        String value = e.attributeValue(XML_CONF_BEAN_CONSTRUCTOR_VALUE);
        String name = e.attributeValue(XML_CONF_BEAN_CONSTRUCTOR_NAME);
        return new ArgumentValue(type, value, name);
    }

    private PropertyValues getPropertyValues(Element element) {
        PropertyValues propertyValues = null;
        List<Element> propertyElements = element.elements(XML_CONF_BEAN_PROPERTY_ELEMENT);
        if (Objects.nonNull(propertyElements) && !propertyElements.isEmpty()) {
            propertyValues = new PropertyValues();
            propertyElements.stream().map(this::getPropertyValue).forEach(propertyValues::addPropertyValue);
        }
        return propertyValues;
    }

    private PropertyValue getPropertyValue(Element e) {
        String type = e.attributeValue(XML_CONF_BEAN_PROPERTY_TYPE);
        String value = e.attributeValue(XML_CONF_BEAN_PROPERTY_VALUE);
        String name = e.attributeValue(XML_CONF_BEAN_PROPERTY_NAME);
        return new PropertyValue(type, value, name);
    }
}
