package com.myspring.test.circle;
/*
 * 测试类，作为bean被ioc创建并完成注入
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
