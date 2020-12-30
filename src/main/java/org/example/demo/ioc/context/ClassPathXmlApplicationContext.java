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

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new URLResource(configLocation));
        for(Map.Entry<String, BeanDefinition> entry : xmlBeanDefinitionReader.getBeanDefinitionMap().entrySet()) {
            // 将map又做成一个Map<String, Map<>>
            this.beanFactory.registerBeanDefinition(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }
}