package tech.demonlee.minis.beans;

import java.util.*;

/**
 * @author Demon.Lee
 * @date 2023-05-21 14:50
 */
public class ArgumentValues {

    private final List<ArgumentValue> argumentValues = new LinkedList<>();

    public ArgumentValues() {
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.argumentValues.get(index);
    }

    public void addArgumentValue(ArgumentValue argumentValue) {
        this.argumentValues.add(argumentValue);
    }

    public int getArgumentCount() {
        return this.argumentValues.size();
    }

    public boolean isEmpty() {
        return this.argumentValues.isEmpty();
    }

    public Params getParams() {
        if (isEmpty()) {
            return null;
        }
        return new Params();
    }

    public class Params {
        private final Class<?>[] types;
        private final Object[] values;

        public Params() {
            int cnt = ArgumentValues.this.getArgumentCount();
            types = new Class[cnt];
            values = new Object[cnt];
            for (int i = 0; i < cnt; i++) {
                ArgumentValue argv = argumentValues.get(i);
                String type = argv.getType();
                Object value = argv.getValue();

                if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
                    types[i] = Integer.class;
                    values[i] = Integer.valueOf((String) value);
                } else if ("int".equals(type)) {
                    types[i] = int.class;
                    values[i] = Integer.valueOf((String) value).intValue();
                } else {
                    types[i] = String.class;
                    values[i] = value;
                }
            }
        }

        public Class<?>[] getTypes() {
            return types;
        }

        public Object[] getValues() {
            return values;
        }
    }
}
