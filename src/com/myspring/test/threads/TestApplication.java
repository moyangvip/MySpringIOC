package com.myspring.test.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.myspring.core.ApplicationContext;
/**
 * @author:	moyang
 * @date:	8/22
 * @Description:支持基本ioc
 * setter注入方式，可注入bean及int、string等基本类型
 * 可选择配置scope、init-method、lazy-init
 * 支持多线程
 * 是否允许循环依赖设置
 * 
 */
/*
 * 此处测试多线程支持
 * */
public class TestApplication {
    
    public static void main(String[] args) {
        ApplicationContext ct = new ApplicationContext(new MyTestResource());
        ExecutorService  executorService = Executors.newFixedThreadPool(20);
        for(int i = 0;i<10;i++)
            executorService.execute(new ThreadTest(ct));
    }
   
}
