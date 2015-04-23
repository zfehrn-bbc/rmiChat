package rmi.model;

import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8934670954363029150L;
	
	private String name;
	
	public User(String msgname) {
		this.setName(msgname);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
