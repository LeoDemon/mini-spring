package tech.demonlee.minis.beans.factory.config;

import java.util.Objects;

/**
 * @author Demon.Lee
 * @date 2023-05-19 07:35
 */
public class ConstructorArgumentValue {

    private Object value;
    private String type;
    private String name;

    public ConstructorArgumentValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public ConstructorArgumentValue(String type, Object value, String name) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstructorArgumentValue that = (ConstructorArgumentValue) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(type, that.type) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type, name);
    }
}
