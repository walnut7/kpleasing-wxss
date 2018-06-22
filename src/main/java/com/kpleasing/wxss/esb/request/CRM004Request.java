package com.kpleasing.wxss.esb.request;

import java.io.Serializable;

public class CRM004Request extends ESBRequestHead implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 6803898998361580231L;
	private String cust_id;
	
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
}
