package org.example.demo.ioc.facotry;

import org.example.demo.ioc.BeanDefinition;

public interface BeanFactory {

    Object getBean(String name);

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
