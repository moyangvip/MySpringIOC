package com.myspring.core;

import java.util.List;


import com.myspring.resource.Resource;
import com.myspring.resource.ResourceLoad;

public class ApplicationContext {
    private ResourceLoad resourceLoad = new ResourceLoad();
    private DefaultBeanFactory beanFactory;
    public ApplicationContext(Resource resource){
    	beanFactory = new DefaultBeanFactory();
        InitBeanFactory(resource);
        beanFactory.refresh();
    }
    public Object getBean(String name){
        return beanFactory.getBean(name);
    }
    private void InitBeanFactory(Resource resource){
        List<BeanDefinition> list = resourceLoad.loadBeanDefinition(resource);
        for(BeanDefinition beanDefinition : list){
            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
    public void close(){
    	beanFactory.close();
    }
}
