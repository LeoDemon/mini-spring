package tech.demonlee.minis.test;

import tech.demonlee.minis.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Demon.Lee
 * @date 2023-08-13 14:09
 */
public class BServiceImpl implements BService {

    @Autowired
    private CService cs;

    public CService getCs() {
        return cs;
    }

    public void setCs(CService cs) {
        this.cs = cs;
    }

    @Override
    public void sayHi() {
        System.out.println("Hi, I am " + this.getClass().getName() + ", and I have a cs: " + getCServiceName());
        if (Objects.nonNull(cs)) {
            cs.greet();
        }
    }

    private String getCServiceName() {
        return Optional.ofNullable(cs).map(v -> v.getClass().getName()).orElse(null);
    }
}
