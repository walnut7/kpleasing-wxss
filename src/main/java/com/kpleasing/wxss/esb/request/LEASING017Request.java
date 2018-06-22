package com.kpleasing.wxss.esb.request;

import java.io.Serializable;

public class LEASING017Request extends ESBRequestHead implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -4752677243932412261L;
	private String external_no;
	private String cust_id;
	private String repay_card_no;
	private String valid_code;
	private String token;
	private String bank_phone;
	
	public String getExternal_no() {
		return external_no;
	}
	
	public void setExternal_no(String external_no) {
		this.external_no = external_no;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getRepay_card_no() {
		return repay_card_no;
	}

	public void setRepay_card_no(String repay_card_no) {
		this.repay_card_no = repay_card_no;
	}

	public String getValid_code() {
		return valid_code;
	}

	public void setValid_code(String valid_code) {
		this.valid_code = valid_code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBank_phone() {
		return bank_phone;
	}

	public void setBank_phone(String bank_phone) {
		this.bank_phone = bank_phone;
	}
}
