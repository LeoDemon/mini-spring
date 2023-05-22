package tech.demonlee.minis.test;

/**
 * @author Demon.Lee
 * @date 2023-05-05 09:54
 */
public class AServiceImpl implements AService {

    private String property1;
    private String property2;
    private String name;
    private int level;

    public AServiceImpl() {
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    @Override
    public void sayHello() {
        System.out.println("A Service say hello 1...");
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "AServiceImpl{" +
                "property1='" + property1 + '\'' +
                ", property2='" + property2 + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
