package tech.demonlee.minis.test;

import java.util.Optional;

/**
 * @author Demon.Lee
 * @date 2023-08-13 14:10
 */
public class CServiceImpl implements CService {

    private AService as;

    public CServiceImpl() {
    }

    public void setAs(AService as) {
        this.as = as;
    }

    @Override
    public void greet() {
        System.out.println("Hey, this is " + this.getClass().getName());
        System.out.println("And, I have a as: " + getAServiceName());
    }

    private String getAServiceName() {
        return Optional.ofNullable(as).map(v -> v.getClass().getName()).orElse(null);
    }
}
