package org.example.demo.ioc;

import org.example.demo.ioc.context.ClassPathXmlApplicationContext;
import org.example.demo.ioc.pojo.HelloWorldService;
import org.example.demo.ioc.pojo.User;
import org.junit.Test;

public class IocTest {

    @Test
    public void testMyIoc() {

        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");

        HelloWorldService helloWorldService = (HelloWorldService) classPathXmlApplicationContext.getBean("helloWorldService");
        helloWorldService.showHelloWorld();

        User user = (User) classPathXmlApplicationContext.getBean("user");
        user.showUser();

        System.out.println(helloWorldService);
        System.out.println(helloWorldService.user);
        System.out.println(helloWorldService.user.helloWorldService);
        System.out.println(helloWorldService.user.helloWorldService.user);
    }

}
