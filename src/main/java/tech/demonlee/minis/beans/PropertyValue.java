package tech.demonlee.minis.beans;

import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-05-19 07:35
 */
public class PropertyValue {

    private String type;
    private Object value;
    private String name;
    private boolean isRef;

    public PropertyValue(String type, Object value, String name) {
        this(type, value, name, false);
    }

    public PropertyValue(String type, Object value, String name, boolean isRef) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(name);
        this.type = type;
        this.value = value;
        this.name = name;
        this.isRef = isRef;
    }

    public Object getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isRef() {
        return isRef;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PropertyValue that = (PropertyValue) obj;
        return Objects.equals(value, that.value) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }
}
