package tech.demonlee.minis.test;

import tech.demonlee.minis.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author Demon.Lee
 * @date 2023-08-13 14:10
 */
public class CServiceImpl implements CService {

    @Autowired
    private AService aservice;

    public CServiceImpl() {
    }

    void init() {
        System.out.println("This is " + this.getClass().getName() + ", and I am init now...");
    }

    public void setAservice(AService aservice) {
        this.aservice = aservice;
    }

    @Override
    public void greet() {
        System.out.println("Hey, this is " + this.getClass().getName());
        System.out.println("And, I have a aservice: " + getAServiceName());
    }

    private String getAServiceName() {
        return Optional.ofNullable(aservice).map(v -> v.getClass().getName()).orElse(null);
    }
}
