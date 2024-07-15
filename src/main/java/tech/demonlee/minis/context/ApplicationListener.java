package tech.demonlee.minis.context;

import java.util.EventListener;

/**
 * @author Demon.Lee
 * @date 2023-12-22 09:26
 */
public class ApplicationListener implements EventListener {

    void onApplicationEvent(ApplicationEvent event) {
        System.out.println("ApplicationListener on event: " + event.toString());
    }
}
