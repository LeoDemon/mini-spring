package tech.demonlee.minis.context;

import java.io.Serial;

/**
 * @author Demon.Lee
 * @date 2023-12-22 09:29
 */
public class ContextRefreshEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
