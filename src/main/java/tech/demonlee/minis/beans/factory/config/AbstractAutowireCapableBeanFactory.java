package tech.demonlee.minis.beans.factory.config;

import tech.demonlee.minis.beans.BeansException;
import tech.demonlee.minis.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-12-14 09:31
 */
public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return beanPostProcessors.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object resultBean = bean;
        for (BeanPostProcessor processor : beanPostProcessors) {
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
        for (BeanPostProcessor processor : beanPostProcessors) {
            processor.setBeanFactory(this);
            resultBean = processor.postProcessAfterInitialization(resultBean, beanName);
            if (Objects.isNull(resultBean)) {
                return null;
            }
        }
        return resultBean;
    }
}
