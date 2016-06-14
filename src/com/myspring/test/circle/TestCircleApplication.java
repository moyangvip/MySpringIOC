package com.myspring.test.circle;

import com.myspring.core.ApplicationContext;
import com.myspring.test.circle.MyCircleResource;
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
 * ����ѭ���������⣬
 * Ĭ�Ͻ�ֹѭ��������A�Ĵ�������B��B��������C��C�Ĵ�������A�����������ѭ����Ӧ�ü�ʱ�״���
 * ������ѭ���������轫CheckCircleDependency�е�enableCircle����Ϊtrue
 * */
public class TestCircleApplication {

    @SuppressWarnings("unused")
	public static void main(String[] args) {
        ApplicationContext ct = new ApplicationContext(new MyCircleResource());
        CircleA circleA = (CircleA)ct.getBean("cirA");
        
        CircleB circleB = (CircleB)ct.getBean("cirB");
    }

}
