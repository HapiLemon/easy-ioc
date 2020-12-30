package org.example.demo.ioc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 顾名思义，BeanDefinition的作用就是对bean的一层封装，封装了bean的名字beanClassName，这个beanClassName是我们从配置文件中读取到的，
 * 反应到上面那个配置文件中也就是"org.example.demo.ioc.pojo.HelloWorldService"和"org.example.demo.ioc.pojo.User"这样的字符串，
 * 我们可以根据这样的字符串把对应的Class对象加载进来，这正是setBeanClassName这个函数干的事。
 * propertyMap里面存的是每一个具体的bean的所有属性值，也就是xml文件中<property>标签里的属性名称和属性值，存在map里面方便以后注入。
 */
public class BeanDefinition {

    private Object bean;

    private Class beanClass;

    private String beanClassName;

    private Map<String, Object> propertyMap = new ConcurrentHashMap<String, Object>();

    public BeanDefinition(String beanClassName){
        setBeanClassName(beanClassName);
    }

    public Object getBean(){
        return this.bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try{
            // 顺便存class
            this.beanClass = Class.forName(beanClassName);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void setProperty(String name, Object value){
        propertyMap.put(name, value);
    }

    public Map<String, Object> getPropertyMap(){
        return this.propertyMap;
    }
}