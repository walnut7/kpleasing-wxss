package com.kpleasing.wxss.protocol.request;

import java.io.Serializable;

public class SyncSaleInfo implements Serializable {

	/** * */
	private static final long serialVersionUID = 1394990719431069899L;

	private String sale_id;
	
	private String sale_phone;
	
	private String sale_name;
	
	private String enabled_flag;

	public String getSale_id() {
		return sale_id;
	}

	public void setSale_id(String sale_id) {
		this.sale_id = sale_id;
	}

	public String getSale_phone() {
		return sale_phone;
	}

	public void setSale_phone(String sale_phone) {
		this.sale_phone = sale_phone;
	}

	public String getSale_name() {
		return sale_name;
	}

	public void setSale_name(String sale_name) {
		this.sale_name = sale_name;
	}

	public String getEnabled_flag() {
		return enabled_flag;
	}

	public void setEnabled_flag(String enabled_flag) {
		this.enabled_flag = enabled_flag;
	}

}
