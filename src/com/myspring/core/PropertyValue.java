package com.myspring.core;
//����bean��ע�����������
public class PropertyValue {
	//����
	public final static int TYPE_VALUE = 0;
	//��������bean
	public final static int TYPE_REF = 1;
	private String name;
	private int type = PropertyValue.TYPE_VALUE;
	private Object value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
