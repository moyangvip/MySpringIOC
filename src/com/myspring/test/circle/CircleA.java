package com.myspring.test.circle;
/*
 * �����࣬��Ϊbean��ioc���������ע��
 * */
public class CircleA {
	private CircleB circleB;
	public void init(){
		System.out.println("create A successful!");
	}
	public CircleB getCircleB() {
		return circleB;
	}

	public void setCircleB(CircleB circleB) {
		this.circleB = circleB;
	}
}
