package com.kpleasing.wxss.pojo;

import java.io.Serializable;

public class SearchParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3374350170067905174L;
	private int page = 1;
	private int pageSize = 20;
	private String custId;
	private String userName;
	private String certId;
	private String phone;
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String toString() {
		return "{page="+this.page+";pageSize="+this.pageSize+";custId="+this.custId+";userName="+this.userName+";certId="+this.certId+";phone="+this.phone+"}";
	}
}
