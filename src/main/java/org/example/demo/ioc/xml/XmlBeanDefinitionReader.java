package org.example.demo.ioc.xml;

import org.example.demo.ioc.BeanDefinition;
import org.example.demo.ioc.BeanReference;
import org.example.demo.ioc.resources.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlBeanDefinitionReader implements BeanDefinitionReader{

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    public XmlBeanDefinitionReader(Resource resource){
        loadBeanDefinition(resource);
    }

    @Override
    public void loadBeanDefinition(Resource resource) {
        InputStream inputStream = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            inputStream = resource.getInputStream();
            Document document = documentBuilder.parse(inputStream);
            // 将 xml 中的bean 放到 map 中
            registerBeanDefinitions(document);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerBeanDefinitions(Document document){
        Element root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for(int i=0; i<nodeList.getLength(); ++i){
            Node node = nodeList.item(i);
            if(node instanceof Element){
                Element beanNode = (Element) node;
                String id = beanNode.getAttribute("id");
                String className = beanNode.getAttribute("class");
                // 存 BeanDefinition 对象
                BeanDefinition beanDefinition = new BeanDefinition(className);
                //设置属性
                processProperties(beanNode, beanDefinition);
                //注册Bean
                beanDefinitionMap.put(id, beanDefinition);
            }
        }
    }

    private void processProperties(Element beanNode, BeanDefinition beanDefinition){
        NodeList nodeList = beanNode.getChildNodes();
        for(int i=0; i<nodeList.getLength(); ++i){
            Node node = nodeList.item(i);
            if(node instanceof Element){
                Element propertyNode = (Element) node;
                String propertyName = propertyNode.getAttribute("name");
                String propertyValue = propertyNode.getAttribute("value");
                if(null!=propertyValue && propertyValue.length()>0) {
                    // 用 map 存 xml 中配置的属性值
                    beanDefinition.setProperty(propertyName, propertyValue);
                }else{
                    // 如果是对象 用 ref
                    String propertyRef = propertyNode.getAttribute("ref");
                    beanDefinition.setProperty(propertyName, new BeanReference(propertyRef));
                }
            }
        }
    }

    public Map<String, BeanDefinition> getBeanDefinitionMap(){
        return beanDefinitionMap;
    }
}
