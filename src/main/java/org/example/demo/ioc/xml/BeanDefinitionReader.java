package org.example.demo.ioc.xml;

import org.example.demo.ioc.resources.Resource;

public interface BeanDefinitionReader {
    void loadBeanDefinition(Resource resource);
}
