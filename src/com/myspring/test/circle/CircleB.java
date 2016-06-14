package com.myspring.test.circle;
/*
 * 测试类，作为bean被ioc创建并完成注入
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
