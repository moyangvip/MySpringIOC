package com.myspring.test;
/*
 * �����࣬��Ϊbean��ioc���������ע��
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
