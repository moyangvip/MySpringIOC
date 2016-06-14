package com.myspring.test;
/*
 * 测试类，作为bean被ioc创建并完成注入
 * */
public class Hello {
    private MessageInt intMsg;
    private MessageString strMsg;
    
    public void init(){
    	String argMsg = intMsg != null ? intMsg.print() :"null!";
    	System.out.println("INIT METHOD,my int msg is:" + argMsg);
    }
    public String print(){
    	StringBuilder sb = new StringBuilder("");
    	if(intMsg != null){
    		sb.append("my " + intMsg.print());
    	}
    	if(strMsg != null){
    		sb.append(";  my " + strMsg.print());
    	}
        return  sb.toString();
    }


	public MessageInt getIntMsg() {
		return intMsg;
	}


	public void setIntMsg(MessageInt intMsg) {
		this.intMsg = intMsg;
	}


	public MessageString getStrMsg() {
		return strMsg;
	}


	public void setStrMsg(MessageString strMsg) {
		this.strMsg = strMsg;
	}
}
