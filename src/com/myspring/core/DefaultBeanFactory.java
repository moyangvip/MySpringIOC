package com.myspring.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class DefaultBeanFactory {
    //�������õ�bean�Ķ��建��
    private BeanDefinitionRegister beanDifinitionRegister = new BeanDefinitionRegister();
    //�������ɺõĵ���bean����
    private SingletonBeanRegister singletonBeanRegister = new SingletonBeanRegister();
    //����ѭ������
    private CheckCircleDependency checkCircleDependency = new CheckCircleDependency();
    //���bean��ʵ�����
    public Object getBean(String beanName){
        if(!beanDifinitionRegister.containsBeanDefinition(beanName)){
               throw new RuntimeException(beanName + "���岻���ڣ�");
        }
        //��ö�Ӧbean��������
        BeanDefinition bd = beanDifinitionRegister.getBeanDefinition(beanName);
        if(bd.getScope() == BeanDefinition.SCOPE_SINGLETON){
            //���Ϊ����������ȥ����������ȥȡ
        	return getSingleton(bd);
        }else if(bd.getScope() == BeanDefinition.SCOPE_PROTOTYPE){
        	//����һ���µ�bean
            return doCreateBean(bd);
        }
        throw new RuntimeException(beanName + "scope ����");
    }
    //����bean�����ö���
    public void registerBeanDefinition(BeanDefinition bd){
        beanDifinitionRegister.registerBeanDefinition(bd);
    }
    //��ɵ������ӳ�beans�ĳ�ʼ������
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
    //���ܵ����ض�ӦbeanΪ�յ�����£�����߳�ͬʱ����getSingleton������ͬ��
    private Object getSingleton(BeanDefinition bd){
        synchronized (singletonBeanRegister) {
        	//�������ٽ����жϣ������ʱ���������ж���ֱ�ӷ���
			if(singletonBeanRegister.containsSingletonBean(bd.getId())){
				return singletonBeanRegister.getSingleton(bd.getId());
			}
			//����ֻ����ͬ�������õ���һ���߳���ɵ�������
			//��������bean������ӵ�����������
			Object object = doCreateBean(bd);
            singletonBeanRegister.registerSingleton(bd.getId(), object);
            return object;
		}
    }
    //��ʼ��ʱԤ���ص������ӳ�bean
    private void preInstantiateSingletons(String name){
    	if(!beanDifinitionRegister.containsBeanDefinition(name)){
            throw new RuntimeException(name + "���岻���ڣ�");
    	}
    	BeanDefinition bd = beanDifinitionRegister.getBeanDefinition(name);
    	//��ʼ��ֻ���ص������Ҹõ�������Ϊ�ӳټ���
    	if(bd.getScope() == BeanDefinition.SCOPE_SINGLETON && !bd.isLazyInit()){
    		getBean(name);
    	}
    }
    private Object doCreateBean(BeanDefinition bd){
    	//����bean
    	Object object = createBean(bd);
    	//�����ע��أ���ֹѭ������
    	checkCircleDependency.beforePopulate(bd.getId(), object);
    	//��ɲ�������ע��
    	populateBean(bd,object);
    	//�˳���ע���
    	checkCircleDependency.afterPopulate(bd.getId());
    	//����bean��ʼ������
    	invokeInitMethods(bd,object);
    	return object;
    }
    //����bean��ʼ������
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
			throw new RuntimeException("�Ҳ�����Ӧ�ĳ�ʼ��������" + initMethodName);
		}
    	
    }
    //��ɲ���ע�빤��
    private void populateBean(BeanDefinition bd,Object bj){
        
    	List<PropertyValue> list = bd.getPropertyValues();
    	if(list !=null && !list.isEmpty()){
    		//�������д�ע���������
    		for(PropertyValue pv:list){
    			switch (pv.getType()) {
    				//ע������bean
					case PropertyValue.TYPE_REF:{
						String refName = (String)pv.getValue();
						//���ѭ���������һ�����õ�bean
	        			Object refbj = checkCircleBean(refName);
	        			//ͨ�����䷽ʽע�����
	        			setPropertyValue(bj,pv,refbj);
	        			break;
					}
					//ע����ͨ����
					case PropertyValue.TYPE_VALUE:{
						Object value = pv.getValue();
						//ͨ�����䷽ʽע�����
						setPropertyValue(bj,pv,value);
						break;
					}
					default:
						throw new RuntimeException("�����ע��������ͣ�" + pv.getType());
				}
        	}	
    	}
    }
    private Object checkCircleBean(String name){
        //֮ǰ��ɹ��쵫δ���ע���bean�������Ƿ���ڸ�bean
        Object bj = checkCircleDependency.checkCircleBean(name);
        if(bj == null){
            //������õ�bean
            bj = getBean(name);
        }
        return bj;
    }
    //ͨ�����䷽ʽע�����
    private void setPropertyValue(Object bj,PropertyValue pv,Object refBj){
    	Class<?> clazz = bj.getClass();
    	PropertyDescriptor pd = getPropertyDescriptor(clazz, pv.getName());
    	try {
			Method setMethod = pd.getWriteMethod();
			setMethod.invoke(bj, refBj);
		} catch (Exception e) {
			throw new RuntimeException("ע��" + pv.getName() + "ʧ��");
		}
    }
    //���get/set����Method
	private PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) {  
        StringBuffer sb = new StringBuffer();//����һ���ɱ��ַ�������������������  
        Method setMethod = null;  
        Method getMethod = null;  
        PropertyDescriptor pd = null;  
        try {  
            Field f = clazz.getDeclaredField(propertyName);//�����ֶ�������ȡ�ֶ�  
            if (f!= null) {  
                //���������ĺ�׺  
               String methodEnd = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);  
               sb.append("set" + methodEnd);//����set����  
               setMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{ f.getType() });  
               sb.delete(0, sb.length());//��������ɱ��ַ���  
               sb.append("get" + methodEnd);//����get����  
               //����get ����  
               getMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{ });  
               //����һ������������ �Ѷ�Ӧ���� propertyName �� get �� set �������浽������������  
               pd = new PropertyDescriptor(propertyName, getMethod, setMethod);  
            }  
        } catch (Exception ex) {  
               throw new RuntimeException("�Ҳ���" + clazz.getSimpleName() + "��Ӧ��"+ propertyName +"���Ե�ע�뷽����");  
        }  
        return pd;  
    }   
	//ͨ�����䷽ʽ����bean����bean��������޲ι��캯��
    private Object createBean(BeanDefinition bd){
        try {  
            Class<?> clazz = Class.forName(bd.getClazz());  
            //ͨ������ʹ���޲�������������Bean  
            return clazz.getConstructor().newInstance();  
        } catch (ClassNotFoundException e) {  
            throw new RuntimeException("û���ҵ�" + bd.getId() + " Bean��" + bd.getClazz() + "��");  
        } catch (Exception e) {  
            throw new RuntimeException("����Bean" + bd.getId() + "ʧ��");  
        }  
    }
}
