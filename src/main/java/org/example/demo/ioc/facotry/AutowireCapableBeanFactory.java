package org.example.demo.ioc.facotry;

import org.example.demo.ioc.BeanDefinition;
import org.example.demo.ioc.BeanReference;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutowireCapableBeanFactory implements BeanFactory{

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    // 拿bean
    @Override
    public Object getBean(String name) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        Object bean = beanDefinition.getBean();
        // bean 还没被赋值 所以为空的
        if(null == bean){
            bean = doCreateBean(beanDefinition);
        }
        return bean;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    // 为bean赋值
    private Object doCreateBean(BeanDefinition beanDefinition) {
        Object bean=null;
        try{
            bean = beanDefinition.getBeanClass().newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        //先设置bean，再注入属性，否则会产生循环依赖
        beanDefinition.setBean(bean);
        applyPropertyValues(bean, beanDefinition.getPropertyMap());
        return bean;
    }

    private void applyPropertyValues(Object bean, Map<String, Object> propertyMap) {

        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            try {
                Field field = bean.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                Object value = entry.getValue();
                if (value instanceof BeanReference) {
                    // 此处为了给对象的对象赋值导致死循环
                    field.set(bean, this.getBean(((BeanReference) value).getRef()));
                } else {
                    String type = field.getType().getName();
                    if (type.equals("java.lang.String")) {
                        field.set(bean, entry.getValue());
                    } else if (type.equals("java.lang.Integer") || type.equals("int")) {
                        field.set(bean, Integer.valueOf((String) entry.getValue()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
