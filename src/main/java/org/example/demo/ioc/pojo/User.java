package org.example.demo.ioc.pojo;

public class User {

    private String name;

    // 产生依赖循环
    public HelloWorldService helloWorldService;

    public void showUser(){
        System.out.println(name);
    }
}