 package com.myspring.test.circle;

import java.util.ArrayList;
import java.util.List;

import com.myspring.core.BeanDefinition;
import com.myspring.core.PropertyValue;
import com.myspring.resource.Resource;
/*
 * ����bean�����ã�������spring�е�xml�е������ļ����˴���������Ϣ��TestApplication�б�����
 * */
//A�Ĵ�������B��B�Ĵ�������A����˽�����ѭ����������ʱ�����׳��쳣

public class MyCircleResource implements Resource{
    private List<BeanDefinition> loadBeanDefinitions = new ArrayList<BeanDefinition>();
    public MyCircleResource(){
    	{
    		/*
    		 * <bean id="cirA" class="com.myspring.test.circle.CircleA" scope="singleton">
    		 * 	<property name="circleB">
    		 * 		<ref bean="cirB"/>
    		 * 	</property>
    		 * </bean>
    		 * 
    		 * */
    		BeanDefinition bd1 = new BeanDefinition();
            bd1.setId("cirA");
            bd1.setClazz("com.myspring.test.circle.CircleA");
            bd1.setScope(BeanDefinition.SCOPE_SINGLETON);
            PropertyValue pv = new PropertyValue();
            pv.setName("circleB");
            pv.setType(PropertyValue.TYPE_REF);
            pv.setValue("cirB");
            bd1.registerPropertyValues(pv);
            loadBeanDefinitions.add(bd1);		
    	}
    	{
    		/*
    		 * <bean id="cirA" class="com.myspring.test.circle.CircleA" scope="singleton">
    		 * 	<property name="circleB">
    		 * 		<ref bean="cirB"/>
    		 * 	</property>
    		 * </bean>
    		 * 
    		 * */
    		BeanDefinition bd1 = new BeanDefinition();
            bd1.setId("cirB");
            bd1.setClazz("com.myspring.test.circle.CircleB");
            bd1.setScope(BeanDefinition.SCOPE_SINGLETON);
            PropertyValue pv = new PropertyValue();
            pv.setName("circleA");
            pv.setType(PropertyValue.TYPE_REF);
            pv.setValue("cirA");
            bd1.registerPropertyValues(pv);
            loadBeanDefinitions.add(bd1);		
    	}
    }
    public List<BeanDefinition> getResource(){
        return loadBeanDefinitions;
    }
}
