package com.kpleasing.wxss.esb.request;

import java.io.Serializable;

public class LEASING009Request extends ESBRequestHead implements Serializable  {
	
	/**	 * 	 */
	private static final long serialVersionUID = 8160443295089719302L;
	private String cust_id;
	
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
}
