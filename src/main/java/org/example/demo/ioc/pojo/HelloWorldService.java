package org.example.demo.ioc.pojo;

public class HelloWorldService {
    private String name;

    private Integer age;

    // 产生依赖循环
    public User user;

    public void showHelloWorld(){
        System.out.println(name+":"+age);
    }
}
