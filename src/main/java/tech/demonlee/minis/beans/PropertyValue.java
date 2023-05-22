package tech.demonlee.minis.beans;

import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-05-19 07:35
 */
public class PropertyValue {

    private Object value;
    private String type;
    private String name;

    public PropertyValue(String name, Object value) {
        this("", value, name);
    }

    public PropertyValue(String type, Object value, String name) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(name);
        this.type = type;
        this.value = value;
        this.name = name;
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
