package com.myspring.core;

import java.util.HashMap;
/*
 * 处理循环依赖
 * */
public class CheckCircleDependency {
    //是否允许循环依赖，默认不允许
    private boolean enableCircle = false;
    //管理已经生成，但尚未完成注入的bean，管理循环依赖，通过ThreadLocal
    private static ThreadLocal<HashMap<String,Object> > localMap = new ThreadLocal<HashMap<String,Object>>(){ 
        @Override 
        protected HashMap<String,Object> initialValue() { 
            System.out.println("Thread:" + Thread.currentThread().getName()+" local map initialValue!"); 
            return new HashMap<String,Object>(); 
        } 
    }; 
    //private Map<String,Object> currCreatingBeans = new HashMap<String,Object>();
    //加入待注入池，防止循环依赖
    public void beforePopulate(String beanName,Object bj){
        HashMap<String,Object> currCreatingBeans = localMap.get();
        if(currCreatingBeans.containsKey(beanName))
            throw new RuntimeException(beanName + "出现循环依赖！");
        currCreatingBeans.put(beanName, bj);
    }
    //完成注入，退出待注入池
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
