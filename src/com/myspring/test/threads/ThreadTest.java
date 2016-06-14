package com.myspring.test.threads;

import com.myspring.core.ApplicationContext;

public class ThreadTest implements Runnable{
    ApplicationContext ct;
    
    public ThreadTest(ApplicationContext ct){
        this.ct = ct;
    }
    public void run() {
        try {
            Thread.sleep(1000);
            Hello hello = (Hello)ct.getBean("helloProtoType");
            System.out.println("Thread:" + Thread.currentThread().getName() + 
                              "  hello(" + hello.hashCode() + "),ref bean:" + hello.print());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
