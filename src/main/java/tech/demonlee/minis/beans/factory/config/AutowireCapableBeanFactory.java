package tech.demonlee.minis.beans.factory.config;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import tech.demonlee.minis.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-08-16 07:03
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object resultBean = bean;
        for (AutowiredAnnotationBeanPostProcessor processor : beanPostProcessors) {
            processor.setBeanFactory(this);
            resultBean = processor.postProcessBeforeInitialization(resultBean, beanName);
            if (Objects.isNull(resultBean)) {
                return null;
            }
        }
        return resultBean;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        Object resultBean = bean;
        for (AutowiredAnnotationBeanPostProcessor processor : beanPostProcessors) {
            processor.setBeanFactory(this);
            resultBean = processor.postProcessAfterInitialization(resultBean, beanName);
            if (Objects.isNull(resultBean)) {
                return null;
            }
        }
        return resultBean;
    }
}
