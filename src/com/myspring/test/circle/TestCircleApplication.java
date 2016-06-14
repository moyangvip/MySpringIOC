package com.myspring.test.circle;

import com.myspring.core.ApplicationContext;
import com.myspring.test.circle.MyCircleResource;
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
 * 测试循环依赖问题，
 * 默认禁止循环依赖，A的创建依赖B，B创建依赖C，C的创建依赖A，则会陷入死循环，应该及时抛错误
 * 若允许循环依赖，需将CheckCircleDependency中的enableCircle设置为true
 * */
public class TestCircleApplication {

    @SuppressWarnings("unused")
	public static void main(String[] args) {
        ApplicationContext ct = new ApplicationContext(new MyCircleResource());
        CircleA circleA = (CircleA)ct.getBean("cirA");
        
        CircleB circleB = (CircleB)ct.getBean("cirB");
    }

}
