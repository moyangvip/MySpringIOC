 package com.myspring.test;

import java.util.ArrayList;
import java.util.List;

import com.myspring.core.BeanDefinition;
import com.myspring.core.PropertyValue;
import com.myspring.resource.Resource;
/*
 * ����bean�����ã�������spring�е�xml�е������ļ����˴���������Ϣ��TestApplication�б�����
 * */
public class MyTestResource implements Resource{
    private List<BeanDefinition> loadBeanDefinitions = new ArrayList<BeanDefinition>();
    public MyTestResource(){
    	{
    		/*
    		 * <bean id="helloSingleton" class="com.myspring.test.Hello" scope="singleton">
    		 * 	<property name="intMsg">
    		 * 		<ref bean="refProtoMsgInt"/>
    		 * 	</property>
    		 * 	<property name="strMsg">
    		 * 		<ref bean="refMsgStr"/>
    		 * 	</property>
    		 * </bean>
    		 * 
    		 * */
    		BeanDefinition bd1 = new BeanDefinition();
            bd1.setId("helloSingleton"); //����bean��ID
            bd1.setClazz("com.myspring.test.Hello"); //����bean��clazz·��
            bd1.setScope(BeanDefinition.SCOPE_SINGLETON); //�����򣺵���
            
            PropertyValue pv = new PropertyValue(); //����ֵ
            pv.setName("intMsg");   //����ֵ��
            pv.setType(PropertyValue.TYPE_REF); //����ֵע�����ͣ�����
            pv.setValue("refProtoMsgInt");  //���õ�bean��ID
            bd1.registerPropertyValues(pv); 
            
            PropertyValue pv2 = new PropertyValue();
            pv2.setName("strMsg");
            pv2.setType(PropertyValue.TYPE_REF);
            pv2.setValue("refMsgStr");
            bd1.registerPropertyValues(pv2);
            loadBeanDefinitions.add(bd1);		
    	}
    	{
    		/*
    		 * <bean id="helloProtoType" class="com.myspring.test.Hello" scope="prototype">
    		 * 	<property name="intMsg">
    		 * 		<ref bean="refProtoMsgInt"/>
    		 * 	</property>
    		 * 	<property name="strMsg">
    		 * 		<ref bean="refMsgStr"/>
    		 * 	</property>
    		 * </bean>
    		 * */
    		BeanDefinition bd2 = new BeanDefinition();
            bd2.setId("helloProtoType");
            bd2.setClazz("com.myspring.test.Hello");
            bd2.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            PropertyValue pv = new PropertyValue();
            pv.setName("intMsg");
            pv.setType(PropertyValue.TYPE_REF);
            pv.setValue("refProtoMsgInt");
            bd2.registerPropertyValues(pv);
            PropertyValue pv2 = new PropertyValue();
            pv2.setName("strMsg");
            pv2.setType(PropertyValue.TYPE_REF);
            pv2.setValue("refMsgStr");
            bd2.registerPropertyValues(pv2);
            loadBeanDefinitions.add(bd2);
    	}
    	{
    		/*
    		 * <bean id="refProtoMsgInt" class="com.myspring.test.MessageInt" scope="prototype">
    		 * 	<property name="args" value="10"/>	
    		 * </bean>
    		 * */
    		BeanDefinition bd3 = new BeanDefinition();
            bd3.setId("refProtoMsgInt");
            bd3.setClazz("com.myspring.test.MessageInt");
            bd3.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            PropertyValue pv2 = new PropertyValue();
            pv2.setName("args");
            pv2.setType(PropertyValue.TYPE_VALUE);
            pv2.setValue(10);
            bd3.registerPropertyValues(pv2);
            loadBeanDefinitions.add(bd3);
    	}
    	{
    		/*
    		 * <bean id="refMsgStr" class="com.myspring.test.MessageString" scope="singleton">
    		 * 	<property name="args" value="msg from msgStr!"/>	
    		 * </bean>
    		 * */
    		BeanDefinition bd3 = new BeanDefinition();
            bd3.setId("refMsgStr");
            bd3.setClazz("com.myspring.test.MessageString");
            bd3.setScope(BeanDefinition.SCOPE_SINGLETON);
            PropertyValue pv2 = new PropertyValue();
            pv2.setName("args");
            pv2.setType(PropertyValue.TYPE_VALUE);
            pv2.setValue("msg from msgStr!");
            bd3.registerPropertyValues(pv2);
            loadBeanDefinitions.add(bd3);
    	}
    	{
    		/*
    		 * <bean id="helloLazyInit" class="com.myspring.test.Hello" init-method="init" scope="prototype" lazy-init="true">
    		 * 	<property name="intMsg">
    		 * 		<ref bean="refProtoMsgInt"/>
    		 * 	</property>
    		 * </bean>
    		 * */
    		BeanDefinition bd2 = new BeanDefinition();
            bd2.setId("helloLazyInit");
            bd2.setClazz("com.myspring.test.Hello");
            bd2.setLazyInit(true);
            bd2.setInitMethod("init");
            
            PropertyValue pv = new PropertyValue();
            pv.setName("intMsg");
            pv.setType(PropertyValue.TYPE_REF);
            pv.setValue("refProtoMsgInt");
            bd2.registerPropertyValues(pv);
            
            loadBeanDefinitions.add(bd2);
    	}
        
    }
    public List<BeanDefinition> getResource(){
        return loadBeanDefinitions;
    }
}
