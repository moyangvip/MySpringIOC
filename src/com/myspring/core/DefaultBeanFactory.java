package com.myspring.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class DefaultBeanFactory {
    //管理配置的bean的定义缓存
    private BeanDefinitionRegister beanDifinitionRegister = new BeanDefinitionRegister();
    //管理生成好的单例bean缓存
    private SingletonBeanRegister singletonBeanRegister = new SingletonBeanRegister();
    //管理循环依赖
    private CheckCircleDependency checkCircleDependency = new CheckCircleDependency();
    //获得bean的实际入口
    public Object getBean(String beanName){
        if(!beanDifinitionRegister.containsBeanDefinition(beanName)){
               throw new RuntimeException(beanName + "定义不存在！");
        }
        //获得对应bean的配置项
        BeanDefinition bd = beanDifinitionRegister.getBeanDefinition(beanName);
        if(bd.getScope() == BeanDefinition.SCOPE_SINGLETON){
            //如果为单例，尝试去单例缓存中去取
        	return getSingleton(bd);
        }else if(bd.getScope() == BeanDefinition.SCOPE_PROTOTYPE){
        	//创建一个新的bean
            return doCreateBean(bd);
        }
        throw new RuntimeException(beanName + "scope 错误！");
    }
    //加载bean的配置定义
    public void registerBeanDefinition(BeanDefinition bd){
        beanDifinitionRegister.registerBeanDefinition(bd);
    }
    //完成单例非延迟beans的初始化加载
    public void refresh(){
        List<String> definitionNames = beanDifinitionRegister.getBeanDefinitionNames();
        for(String name :definitionNames){
        	preInstantiateSingletons(name);
        }
    }
    public void close(){
    	singletonBeanRegister.close();
    	beanDifinitionRegister.close();
    }
    //可能单例池对应bean为空的情况下，多个线程同时调用getSingleton，加锁同步
    private Object getSingleton(BeanDefinition bd){
        synchronized (singletonBeanRegister) {
        	//加锁后再进行判断，如果此时单例池已有对象，直接返回
			if(singletonBeanRegister.containsSingletonBean(bd.getId())){
				return singletonBeanRegister.getSingleton(bd.getId());
			}
			//否则只能在同步块中让单独一个线程完成单例生成
			//创建单例bean，并添加到单例缓存中
			Object object = doCreateBean(bd);
            singletonBeanRegister.registerSingleton(bd.getId(), object);
            return object;
		}
    }
    //初始化时预加载单例非延迟bean
    private void preInstantiateSingletons(String name){
    	if(!beanDifinitionRegister.containsBeanDefinition(name)){
            throw new RuntimeException(name + "定义不存在！");
    	}
    	BeanDefinition bd = beanDifinitionRegister.getBeanDefinition(name);
    	//初始化只加载单例，且该单例不能为延迟加载
    	if(bd.getScope() == BeanDefinition.SCOPE_SINGLETON && !bd.isLazyInit()){
    		getBean(name);
    	}
    }
    private Object doCreateBean(BeanDefinition bd){
    	//构造bean
    	Object object = createBean(bd);
    	//加入待注入池，防止循环依赖
    	checkCircleDependency.beforePopulate(bd.getId(), object);
    	//完成参数依赖注入
    	populateBean(bd,object);
    	//退出待注入池
    	checkCircleDependency.afterPopulate(bd.getId());
    	//调用bean初始化函数
    	invokeInitMethods(bd,object);
    	return object;
    }
    //调用bean初始化函数
    private void invokeInitMethods(BeanDefinition bd,Object bj){
    	String initMethodName = bd.getInitMethod();
    	if(initMethodName == null){
    		return ;
    	}
    	Class<?> clazz = bj.getClass();
    	try {
			Method initMethod = clazz.getDeclaredMethod(initMethodName);
			initMethod.invoke(bj);
		} catch (Exception e) {
			throw new RuntimeException("找不到对应的初始化方法：" + initMethodName);
		}
    	
    }
    //完成参数注入工作
    private void populateBean(BeanDefinition bd,Object bj){
        
    	List<PropertyValue> list = bd.getPropertyValues();
    	if(list !=null && !list.isEmpty()){
    		//遍历所有待注入参数配置
    		for(PropertyValue pv:list){
    			switch (pv.getType()) {
    				//注入引用bean
					case PropertyValue.TYPE_REF:{
						String refName = (String)pv.getValue();
						//检查循环依赖，且获得引用的bean
	        			Object refbj = checkCircleBean(refName);
	        			//通过反射方式注入参数
	        			setPropertyValue(bj,pv,refbj);
	        			break;
					}
					//注入普通常量
					case PropertyValue.TYPE_VALUE:{
						Object value = pv.getValue();
						//通过反射方式注入参数
						setPropertyValue(bj,pv,value);
						break;
					}
					default:
						throw new RuntimeException("错误的注入参数类型：" + pv.getType());
				}
        	}	
    	}
    }
    private Object checkCircleBean(String name){
        //之前完成构造但未完成注入的bean缓存中是否存在该bean
        Object bj = checkCircleDependency.checkCircleBean(name);
        if(bj == null){
            //获得引用的bean
            bj = getBean(name);
        }
        return bj;
    }
    //通过反射方式注入参数
    private void setPropertyValue(Object bj,PropertyValue pv,Object refBj){
    	Class<?> clazz = bj.getClass();
    	PropertyDescriptor pd = getPropertyDescriptor(clazz, pv.getName());
    	try {
			Method setMethod = pd.getWriteMethod();
			setMethod.invoke(bj, refBj);
		} catch (Exception e) {
			throw new RuntimeException("注入" + pv.getName() + "失败");
		}
    }
    //获得get/set函数Method
	private PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) {  
        StringBuffer sb = new StringBuffer();//构建一个可变字符串用来构建方法名称  
        Method setMethod = null;  
        Method getMethod = null;  
        PropertyDescriptor pd = null;  
        try {  
            Field f = clazz.getDeclaredField(propertyName);//根据字段名来获取字段  
            if (f!= null) {  
                //构建方法的后缀  
               String methodEnd = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);  
               sb.append("set" + methodEnd);//构建set方法  
               setMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{ f.getType() });  
               sb.delete(0, sb.length());//清空整个可变字符串  
               sb.append("get" + methodEnd);//构建get方法  
               //构建get 方法  
               getMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{ });  
               //构建一个属性描述器 把对应属性 propertyName 的 get 和 set 方法保存到属性描述器中  
               pd = new PropertyDescriptor(propertyName, getMethod, setMethod);  
            }  
        } catch (Exception ex) {  
               throw new RuntimeException("找不到" + clazz.getSimpleName() + "对应的"+ propertyName +"属性的注入方法：");  
        }  
        return pd;  
    }   
	//通过反射方式构造bean，该bean必须存在无参构造函数
    private Object createBean(BeanDefinition bd){
        try {  
            Class<?> clazz = Class.forName(bd.getClazz());  
            //通过反射使用无参数构造器创建Bean  
            return clazz.getConstructor().newInstance();  
        } catch (ClassNotFoundException e) {  
            throw new RuntimeException("没有找到" + bd.getId() + " Bean的" + bd.getClazz() + "类");  
        } catch (Exception e) {  
            throw new RuntimeException("创建Bean" + bd.getId() + "失败");  
        }  
    }
}
