package tech.demonlee.minis.beans.factory.xml;

import org.dom4j.Element;
import tech.demonlee.minis.beans.PropertyValue;
import tech.demonlee.minis.beans.PropertyValues;
import tech.demonlee.minis.beans.factory.config.BeanDefinition;
import tech.demonlee.minis.beans.factory.config.ConstructorArgumentValue;
import tech.demonlee.minis.beans.factory.config.ConstructorArgumentValues;
import tech.demonlee.minis.beans.factory.support.AbstractBeanFactory;
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
    private static final String XML_CONF_BEAN_LAZY_INIT = "lazyInit";
    private static final String XML_CONF_BEAN_INIT_METHOD = "init-method";
    private static final String XML_CONF_BEAN_CONSTRUCTOR_ELEMENT = "constructor-arg";
    private static final String XML_CONF_BEAN_CONSTRUCTOR_TYPE = "type";
    private static final String XML_CONF_BEAN_CONSTRUCTOR_VALUE = "value";
    private static final String XML_CONF_BEAN_CONSTRUCTOR_NAME = "name";
    private static final String XML_CONF_BEAN_PROPERTY_ELEMENT = "property";
    private static final String XML_CONF_BEAN_PROPERTY_TYPE = "type";
    private static final String XML_CONF_BEAN_PROPERTY_VALUE = "value";
    private static final String XML_CONF_BEAN_PROPERTY_NAME = "name";
    private static final String XML_CONF_BEAN_PROPERTY_REF = "ref";

    AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue(XML_CONF_BEAN_ID);
            String beanClassName = element.attributeValue(XML_CONF_BEAN_CLASS_NAME);
            String lazyInitStr = element.attributeValue(XML_CONF_BEAN_LAZY_INIT);
            String initMethod = element.attributeValue(XML_CONF_BEAN_INIT_METHOD);
            boolean lazyInit = Boolean.parseBoolean(lazyInitStr);

            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            beanDefinition.setLazyInit(lazyInit);
            beanDefinition.setInitMethodName(initMethod);

            ConstructorArgumentValues argumentValues = getArgumentValues(element);
            beanDefinition.setConstructorArgumentValues(argumentValues);

            PropertyValues propertyValues = getPropertyValues(element);
            if (Objects.nonNull(propertyValues)) {
                beanDefinition.setPropertyValues(propertyValues);
                List<String> refs = propertyValues.getPropertyValueList().stream()
                        .filter(PropertyValue::isRef)
                        .map(v -> v.getValue().toString())
                        .toList();
                if (!refs.isEmpty()) {
                    String[] refArr = refs.toArray(new String[0]);
                    beanDefinition.setDependsOn(refArr);
                }
            }

            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

    private ConstructorArgumentValues getArgumentValues(Element element) {
        ConstructorArgumentValues argumentValues = null;
        List<Element> argElements = element.elements(XML_CONF_BEAN_CONSTRUCTOR_ELEMENT);
        if (Objects.nonNull(argElements) && !argElements.isEmpty()) {
            argumentValues = new ConstructorArgumentValues();
            argElements.stream().map(this::getArgumentValue).forEach(argumentValues::addArgumentValue);
        }
        return argumentValues;
    }

    private ConstructorArgumentValue getArgumentValue(Element e) {
        String type = e.attributeValue(XML_CONF_BEAN_CONSTRUCTOR_TYPE);
        String value = e.attributeValue(XML_CONF_BEAN_CONSTRUCTOR_VALUE);
        String name = e.attributeValue(XML_CONF_BEAN_CONSTRUCTOR_NAME);
        return new ConstructorArgumentValue(type, value, name);
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
        String ref = e.attributeValue(XML_CONF_BEAN_PROPERTY_REF);

        boolean isRef = false;
        if (Objects.nonNull(ref)) {
            value = ref;
            isRef = true;
        }

        return new PropertyValue(type, value, name, isRef);
    }
}
