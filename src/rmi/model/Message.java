package rmi.model;

import java.io.Serializable;

public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3436216238279933152L;
	public String name;
	public String msg;
	
	public Message(String name, String msg) {
		this.setName(name);
		this.setMsg(msg);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
