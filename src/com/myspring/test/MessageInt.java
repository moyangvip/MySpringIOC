package com.myspring.test;
/*
 * �����࣬��Ϊbean��ioc���������ע��
 * */
public class MessageInt {
    private int args;

    public int getArgs() {
        return args;
    }

    public void setArgs(int args) {
        this.args = args;
    }
    public String print(){
        return new String("messageInt(" + this.hashCode() + "):" + args);
    }
}
