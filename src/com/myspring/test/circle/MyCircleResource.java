 package com.myspring.test.circle;

import java.util.ArrayList;
import java.util.List;

import com.myspring.core.BeanDefinition;
import com.myspring.core.PropertyValue;
import com.myspring.resource.Resource;
/*
 * 设置bean的配置，类似于spring中的xml中的配置文件，此处的配置信息在TestApplication中被处理
 * */
//A的创建依赖B，B的创建依赖A，因此将产生循环依赖，此时程序将抛出异常

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
