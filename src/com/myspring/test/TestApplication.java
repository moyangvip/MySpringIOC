package com.myspring.test;

import com.myspring.core.ApplicationContext;
/**
 * @author:	moyang
 * @date:	8/22
 * @Description:֧�ֻ���ioc
 * setterע�뷽ʽ����ע��bean��int��string�Ȼ�������
 * ��ѡ������scope��init-method��lazy-init
 * ֧�ֶ��߳�
 * �Ƿ�����ѭ����������
 * 
 */
/*
 * �˴������������ioc���ܣ�����setterע�뷽ʽ����ע��bean��int��string�Ȼ������ͣ�scope��init-method��lazy-init
 * */
public class TestApplication {

    public static void main(String[] args) {

        ApplicationContext ct = new ApplicationContext(new MyTestResource());
        System.out.println("this is singleton bean:");
        Hello hello1 = (Hello) ct.getBean("helloSingleton");
        System.out.println("hello1(" + hello1.hashCode() + "),ref bean:" + hello1.print());
        Hello hello2 = (Hello) ct.getBean("helloSingleton");
        System.out.println("hello2(" + hello2.hashCode() + "),ref bean:" + hello2.print());
 
        System.out.println("--------------------------");
        System.out.println("this is protoType bean:");
        Hello hello3 = (Hello) ct.getBean("helloProtoType");
        System.out.println("hello3(" + hello3.hashCode() + "),ref bean:" + hello3.print());
        Hello hello4 = (Hello) ct.getBean("helloProtoType");
        System.out.println("hello4(" + hello4.hashCode() + "),ref bean:" + hello4.print());
        
        
        System.out.println("--------------------------");
        System.out.println("this is Lazy singleton bean with init:");
        Hello hello5 = (Hello) ct.getBean("helloLazyInit");
        System.out.println("hello5(" + hello5.hashCode() + "),ref bean:" + hello5.print());
    }

}
