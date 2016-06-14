package com.myspring.core;

import java.util.HashMap;
import java.util.Map;

public class SingletonBeanRegister {
	//已创建完成的单例bean的容器
    private final Map<String, Object> createdBeans = new HashMap<String, Object>();
    public synchronized Object getSingleton(String beanName) {  
        if(!createdBeans.containsKey(beanName)) {  
            throw new RuntimeException( beanName + " 不存在");  
        }  
        return createdBeans.get(beanName);  
    } 
    //注册单例，返回实际完成注册的单例
    public synchronized Object registerSingleton(String beanName,Object bean) {  
        if(createdBeans.containsKey(beanName)) {
        	//多线程下，两个线程同时doCreateBean，将只会注册最先完成的bean,并返回单例池中的bean
            return createdBeans.get(beanName);
        }  
        createdBeans.put(beanName, bean);
        return bean;
    }  
    public synchronized boolean containsSingletonBean(String beanName){
        return createdBeans.containsKey(beanName);
    }
    public synchronized void close(){
    	createdBeans.clear();
    }
}

