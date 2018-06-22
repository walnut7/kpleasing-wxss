package com.kpleasing.wxss.esb.request;

public class LEASING001Request extends ESBRequestHead {
	private String phone;
	private String content;
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
