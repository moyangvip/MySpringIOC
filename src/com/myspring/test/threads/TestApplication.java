package com.myspring.test.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * �˴����Զ��߳�֧��
 * */
public class TestApplication {
    
    public static void main(String[] args) {
        ApplicationContext ct = new ApplicationContext(new MyTestResource());
        ExecutorService  executorService = Executors.newFixedThreadPool(20);
        for(int i = 0;i<10;i++)
            executorService.execute(new ThreadTest(ct));
    }
   
}
