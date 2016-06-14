package com.myspring.core;

import java.util.HashMap;
import java.util.Map;

public class SingletonBeanRegister {
	//�Ѵ�����ɵĵ���bean������
    private final Map<String, Object> createdBeans = new HashMap<String, Object>();
    public synchronized Object getSingleton(String beanName) {  
        if(!createdBeans.containsKey(beanName)) {  
            throw new RuntimeException( beanName + " ������");  
        }  
        return createdBeans.get(beanName);  
    } 
    //ע�ᵥ��������ʵ�����ע��ĵ���
    public synchronized Object registerSingleton(String beanName,Object bean) {  
        if(createdBeans.containsKey(beanName)) {
        	//���߳��£������߳�ͬʱdoCreateBean����ֻ��ע��������ɵ�bean,�����ص������е�bean
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

