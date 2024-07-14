package tech.demonlee.minis.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.config.BeanFactoryPostProcessor;
import tech.demonlee.minis.beans.factory.config.BeanPostProcessor;
import tech.demonlee.minis.beans.factory.config.ConfigurableListableBeanFactory;
import tech.demonlee.minis.core.env.Environment;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private Environment environment;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    private long startupDate;
    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public boolean containsBean(String beanName) {
        return getBeanFactory().containsBean(beanName);
    }

    @Override
    public Class<?> getType(String beanName) {
        return getBeanFactory().getType(beanName);
    }

    @Override
    public boolean isPrototype(String beanName) {
        return getBeanFactory().isPrototype(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return getBeanFactory().isSingleton(beanName);
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return beanFactoryPostProcessors;
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        postProcessBeanFactory(getBeanFactory());
        registerBeanPostProcessors(getBeanFactory());
        initApplicationEventPublisher();
        onRefresh();
        registerListeners();
        finishRefresh();
    }

    abstract void registerListeners();

    abstract void initApplicationEventPublisher();

    abstract void postProcessBeanFactory(ConfigurableListableBeanFactory bf);

    abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory bf);

    abstract void onRefresh();

    abstract void finishRefresh();

    @Override
    public boolean containsSingleton(String beanName) {
        return getBeanFactory().containsSingleton(beanName);
    }

    @Override
    public Object getSingleton(String beanName) {
        return getBeanFactory().getSingleton(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return getBeanFactory().getSingletonNames();
    }

    @Override
    public void registerSingleton(String beanName, Object singleObject) {
        getBeanFactory().registerSingleton(beanName, singleObject);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        getBeanFactory().addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return getBeanFactory().getBeanPostProcessorCount();
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return getBeanFactory().getDependenciesForBean(beanName);
    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return getBeanFactory().getDependentBeans(beanName);
    }

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        getBeanFactory().registerDependentBean(beanName, dependentBeanName);
    }

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public void setEnvironment(Environment env) {
        this.environment = env;
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor processor) {
        this.beanFactoryPostProcessors.add(processor);
    }

    @Override
    public void close() {
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
