package tech.demonlee.minis.beans.factory.support;

import tech.demonlee.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Demon.Lee
 * @date 2023-05-17 13:29
 * @desc
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected List<String> beanNames = new ArrayList<>(256);
    protected final Map<String, Object> singletons = new ConcurrentHashMap<>(256);
    protected final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
    protected final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singleObject) {
        synchronized (this.singletons) {
            this.singletons.put(beanName, singleObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return this.beanNames.toArray(new String[0]);
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.singletons.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }

    public void registerDependentBean(String beanName, String dependentBeanName) {
        if (hasRegisteredDependentBean(beanName, dependentBeanName)) {
            System.out.println(dependentBeanName + " has been registered for " + beanName);
            return;
        }

        registerDependentBean(this.dependentBeanMap, beanName, dependentBeanName);
        registerDependentBean(this.dependenciesForBeanMap, dependentBeanName, beanName);
    }

    private void registerDependentBean(final Map<String, Set<String>> beanMap,
                                       String beanName, String dependentBeanName) {
        synchronized (beanMap) {
            Set<String> dependentBeans = beanMap.get(beanName);
            if (Objects.isNull(dependentBeans)) {
                dependentBeans = new LinkedHashSet<>(8);
                beanMap.put(beanName, dependentBeans);
            }
            dependentBeans.add(dependentBeanName);
        }
    }

    private boolean hasRegisteredDependentBean(String beanName, String dependentBeanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (Objects.isNull(dependentBeans)) {
            return false;
        }
        return dependentBeans.contains(dependentBeanName);
    }

    public String[] getDependentBeans(String beanName) {
        Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
        if (Objects.isNull(dependentBeans)) {
            return new String[0];
        }
        return dependentBeans.toArray(new String[0]);
    }

    public String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
        if (Objects.isNull(dependenciesForBean)) {
            return new String[0];
        }
        return dependenciesForBean.toArray(new String[0]);
    }
}
