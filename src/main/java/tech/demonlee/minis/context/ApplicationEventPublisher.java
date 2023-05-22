package tech.demonlee.minis.context;

/**
 * @author Demon.Lee
 * @date 2023-05-19 06:46
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);
}
