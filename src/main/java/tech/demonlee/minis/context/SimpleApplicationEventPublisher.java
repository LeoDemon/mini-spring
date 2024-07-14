package tech.demonlee.minis.context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Demon.Lee
 * @date 2023-12-22 09:41
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {

    private final List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        listeners.add(listener);
    }
}
