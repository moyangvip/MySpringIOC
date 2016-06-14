package com.myspring.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class BeanDefinitionRegister {
    //bean的配置的定义
    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>();
    private List<String> beanDefinitionNames = new Vector<String>();
    public synchronized void registerBeanDefinition(BeanDefinition definition){
        if(beanDefinitions.containsKey(definition.getId())){
            throw new RuntimeException("已存在bean定义");
        }
        beanDefinitions.put(definition.getId(), definition);
        beanDefinitionNames.add(definition.getId());
    }
    public synchronized BeanDefinition getBeanDefinition(String beanName){
        if(!beanDefinitions.containsKey(beanName))
            throw new RuntimeException(beanName + "BeanDefinition 不存在！");
        return beanDefinitions.get(beanName);
    }
    public synchronized boolean containsBeanDefinition(String beanName){
        return beanDefinitions.containsKey(beanName);
    }
    public synchronized List<String> getBeanDefinitionNames(){
        return beanDefinitionNames;
    }
    public synchronized void close(){
    	beanDefinitions.clear();
    	beanDefinitionNames.clear();
    }
    
}
