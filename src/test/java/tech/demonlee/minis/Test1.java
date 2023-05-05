package tech.demonlee.minis;

import tech.demonlee.minis.test.AService;

/**
 * @author Demon.Lee
 * @date 2023-05-05 10:01
 */
public class Test1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) context.getBean("aservice");
        aService.sayHello();
    }
}
