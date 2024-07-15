package tech.demonlee.minis.context;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import tech.demonlee.minis.beans.factory.config.BeanPostProcessor;
import tech.demonlee.minis.beans.factory.config.ConfigurableListableBeanFactory;
import tech.demonlee.minis.beans.factory.support.DefaultListableBeanFactory;
import tech.demonlee.minis.beans.factory.xml.XmlBeanDefinitionReader;
import tech.demonlee.minis.core.ClassPathXmlResource;
import tech.demonlee.minis.core.Resource;

/**
 * @author Demon.Lee
 * @date 2023-04-26 09:09
 * @desc app service for assembling, here is dispatch center
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    // 引入 BeanFactory 来获得对应的能力，而不是直接实现对应的方法
    DefaultListableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        // 从外部加载资源文件
        Resource resource = new ClassPathXmlResource(fileName);

        // 构造默认的 BeanFactory 核心类：获取 Bean 和 BeanDefinition
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();

        // 将 BeanDefinition 注册到 BeanFactory
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        reader.loadBeanDefinitions(resource);

        this.beanFactory = defaultListableBeanFactory;

        if (isRefresh) {
            try {
                refresh();
            } catch (IllegalStateException | BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    void initApplicationEventPublisher() {
        ApplicationEventPublisher publisher = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(publisher);
    }

    @Override
    void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {
        System.out.println("postProcessBeanFactory to be finished...");
    }

    @Override
    void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        BeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        bf.addBeanPostProcessor(processor);
    }

    @Override
    void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        addApplicationListener(listener);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    void finishRefresh() {
        ApplicationEvent event = new ContextRefreshEvent("Context Refreshed...");
        publishEvent(event);
    }
}
