package tech.demonlee.minis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Demon.Lee
 * @date 2023-04-26 09:09
 */
public class ClassPathXmlApplicationContext {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    /*
     <?xml version="1.0" encoding="UTF-8" ?>
     <beans>
         <bean id = "xxxid" class = "com.minis.xxxclass"></bean>
         <bean id = "xxyid" class = "com.minis.xxyclass"></bean>
     </beans>
     */
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        Document document;
        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        Element rootElement = document.getRootElement();
        beanDefinitions = rootElement.elements().stream()
                .map(element -> {
                    String id = element.attributeValue("id");
                    String className = element.attributeValue("class");
                    if (Objects.isNull(id) || id.trim().isEmpty() ||
                            Objects.isNull(className) || className.trim().isEmpty()) {
                        System.out.println("id or class is empty");
                        return null;
                    }
                    return new BeanDefinition(id, className);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void instanceBeans() {
        beanDefinitions.forEach(beanDefinition -> {
            try {
                Object bean = Class.forName(beanDefinition.getClassName()).getDeclaredConstructor().newInstance();
                singletons.put(beanDefinition.getId(), bean);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }
}
