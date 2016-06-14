package com.myspring.resource;

import java.util.List;

import com.myspring.core.BeanDefinition;


public class ResourceLoad {
    public List<BeanDefinition> loadBeanDefinition(Resource resource){
        return resource.getResource();
    }
    
}
