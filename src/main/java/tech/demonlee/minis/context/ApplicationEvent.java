package tech.demonlee.minis.context;

import java.io.Serial;
import java.util.EventObject;

/**
 * @author Demon.Lee
 * @date 2023-05-19 06:37
 */
public class ApplicationEvent extends EventObject {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
