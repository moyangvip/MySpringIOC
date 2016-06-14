package com.myspring.test;
/*
 * 测试类，作为bean被ioc创建并完成注入
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
