package com.myspring.test;
/*
 * 测试类，作为bean被ioc创建并完成注入
 * */
public class MessageString {
	private String args;

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
    public String print(){
        return new String("messageStr(" + this.hashCode() + "):" + args);
    }
}
