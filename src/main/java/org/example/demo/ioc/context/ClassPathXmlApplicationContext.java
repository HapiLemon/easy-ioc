package org.example.demo.ioc.context;

import org.example.demo.ioc.BeanDefinition;
import org.example.demo.ioc.facotry.AutowireCapableBeanFactory;
import org.example.demo.ioc.facotry.BeanFactory;
import org.example.demo.ioc.resources.URLResource;
import org.example.demo.ioc.xml.XmlBeanDefinitionReader;

import java.util.Map;

public class ClassPathXmlApplicationContext implements ApplicationContext{

    private String configLocation;

    private BeanFactory beanFactory;

    // 构造函数
    public ClassPathXmlApplicationContext(String configLocation){
        // 构造了一个空的 AutowireCapableBeanFactory 对象
        this(configLocation, new AutowireCapableBeanFactory());
    }

    // 构造函数
    public ClassPathXmlApplicationContext(String configLocation, BeanFactory beanFactory){
        this.configLocation = configLocation;
        this.beanFactory = beanFactory;

        refresh();
    }

    private void refresh(){

        // 得到一个resources对象 只是为了后面能将配置文件以InputStream对象的方式读出
        URLResource urlResource = new URLResource(configLocation);
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(urlResource);
        for(Map.Entry<String, BeanDefinition> entry : xmlBeanDefinitionReader.getBeanDefinitionMap().entrySet()) {
            // 将map放到beanFactory的实现类的成员变量里面
            this.beanFactory.registerBeanDefinition(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }
}