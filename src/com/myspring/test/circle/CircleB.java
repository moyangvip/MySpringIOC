package com.myspring.test.circle;
/*
 * �����࣬��Ϊbean��ioc���������ע��
 * */
public class CircleB {
	private CircleA circleA;
	public void init(){
		System.out.println("create B successful!");
	}
	public CircleA getCircleA() {
		return circleA;
	}

	public void setCircleA(CircleA circleA) {
		this.circleA = circleA;
	}
}
