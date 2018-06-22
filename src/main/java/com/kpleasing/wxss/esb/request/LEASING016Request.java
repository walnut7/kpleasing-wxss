package com.kpleasing.wxss.esb.request;

import java.io.Serializable;

public class LEASING016Request extends ESBRequestHead implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -1207245712803439440L;
	private String external_no;
	private String cust_id;
	private String cust_name;
	private String repay_card_no;
	private String cert_type;
	private String cert_code;
	private String bank_phone;
	
	/**
	 * @return the external_no
	 */
	public String getExternal_no() {
		return external_no;
	}
	
	/**
	 * @param external_no the external_no to set
	 */
	public void setExternal_no(String external_no) {
		this.external_no = external_no;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getRepay_card_no() {
		return repay_card_no;
	}

	public void setRepay_card_no(String repay_card_no) {
		this.repay_card_no = repay_card_no;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_code() {
		return cert_code;
	}

	public void setCert_code(String cert_code) {
		this.cert_code = cert_code;
	}

	public String getBank_phone() {
		return bank_phone;
	}

	public void setBank_phone(String bank_phone) {
		this.bank_phone = bank_phone;
	}
}
