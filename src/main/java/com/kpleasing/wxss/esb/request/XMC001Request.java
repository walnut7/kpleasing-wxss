package com.kpleasing.wxss.esb.request;

import java.io.Serializable;

public class XMC001Request extends ESBRequestHead implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -5469435130927116547L;
	private String cust_id;
	
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

}
