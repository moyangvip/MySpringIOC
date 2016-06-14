package com.myspring.core;

import java.util.ArrayList;
import java.util.List;

/*
 * ����ÿ��bean�����õĶ��壬bean�������޲ι��캯��
 * */
public class BeanDefinition {
	//����ģʽ
    public static final int SCOPE_SINGLETON = 0;
    //ԭ��ģʽ
    public static final int SCOPE_PROTOTYPE = 1;
    private String id;
    private String clazz;
    //Ĭ��Ϊ����ģʽ
    private int scope = SCOPE_SINGLETON;
    //Ĭ�Ϲر��ӳټ���
    private boolean lazyInit = false;
    private String initMethod;
    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>(); 
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getClazz() {
        return clazz;
    }
    
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
    
    public int getScope() {
        return scope;
    }
    
    public void setScope(int scope) {
        this.scope = scope;
    }

	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}
	
	public void registerPropertyValues(PropertyValue pv) {
		propertyValues.add(pv);
	}

	public boolean isLazyInit() {
		return lazyInit;
	}

	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}

	public String getInitMethod() {
		return initMethod;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}
}
