package tech.demonlee.minis.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Demon.Lee
 * @date 2023-05-21 14:26
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(64);
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

    public int size() {
        return this.propertyValueList.size();
    }

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public void addPropertyValue(String propertyName, Object propertyValue) {
        this.propertyValueList.add(new PropertyValue(propertyName, propertyValue));
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    public Object get(String propertyName) {
        PropertyValue pv = this.getPropertyValue(propertyName);
        return Optional.ofNullable(pv).map(PropertyValue::getValue).orElse(null);
    }

    public boolean contains(String propertyName) {
        return Objects.nonNull(this.getPropertyValue(propertyName));
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }

    public void removePropertyValve(PropertyValue pv) {
        this.propertyValueList.remove(pv);
    }

    public void removePropertyValve(String propertyName) {
        PropertyValue pv = this.getPropertyValue(propertyName);
        this.removePropertyValve(pv);
    }

    public PropertyValues.Params getParams() {
        if (isEmpty()) {
            return null;
        }
        return new PropertyValues.Params();
    }

    public class Params {
        private final Class<?>[] types;
        private final Object[] values;
        private final String[] methods;

        public Params() {
            int cnt = PropertyValues.this.size();
            this.types = new Class[cnt];
            this.values = new Object[cnt];
            this.methods = new String[cnt];
            for (int i = 0; i < cnt; i++) {
                PropertyValue pv = propertyValueList.get(i);
                String type = pv.getType();
                Object value = pv.getValue();
                String name = pv.getName();

                if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
                    types[i] = Integer.class;
                } else if ("int".equals(type)) {
                    types[i] = int.class;
                } else {
                    types[i] = String.class;
                }
                values[i] = value;
                methods[i] = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        }

        public Class<?>[] getTypes() {
            return types;
        }

        public Object[] getValues() {
            return values;
        }

        public String[] getMethods() {
            return methods;
        }
    }

}
