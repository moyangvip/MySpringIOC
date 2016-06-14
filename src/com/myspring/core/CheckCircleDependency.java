package com.myspring.core;

import java.util.HashMap;
/*
 * ����ѭ������
 * */
public class CheckCircleDependency {
    //�Ƿ�����ѭ��������Ĭ�ϲ�����
    private boolean enableCircle = false;
    //�����Ѿ����ɣ�����δ���ע���bean������ѭ��������ͨ��ThreadLocal
    private static ThreadLocal<HashMap<String,Object> > localMap = new ThreadLocal<HashMap<String,Object>>(){ 
        @Override 
        protected HashMap<String,Object> initialValue() { 
            System.out.println("Thread:" + Thread.currentThread().getName()+" local map initialValue!"); 
            return new HashMap<String,Object>(); 
        } 
    }; 
    //private Map<String,Object> currCreatingBeans = new HashMap<String,Object>();
    //�����ע��أ���ֹѭ������
    public void beforePopulate(String beanName,Object bj){
        HashMap<String,Object> currCreatingBeans = localMap.get();
        if(currCreatingBeans.containsKey(beanName))
            throw new RuntimeException(beanName + "����ѭ��������");
        currCreatingBeans.put(beanName, bj);
    }
    //���ע�룬�˳���ע���
    public void afterPopulate(String beanName){
        HashMap<String,Object> currCreatingBeans = localMap.get();
        currCreatingBeans.remove(beanName);
    }
    public Object checkCircleBean(String beanName){
        HashMap<String,Object> currCreatingBeans = localMap.get();
        if(enableCircle && currCreatingBeans.containsKey(beanName)){
            return currCreatingBeans.get(beanName);
        }
        return null;
    }
    public boolean isEnableCircle() {
        return enableCircle;
    }
    public void setEnableCircle(boolean enableCircle) {
        this.enableCircle = enableCircle;
    }
}
