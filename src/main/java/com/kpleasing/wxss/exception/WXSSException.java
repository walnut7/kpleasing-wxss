package com.kpleasing.wxss.exception;

/**
 * 
 * @author howard.huang
 *
 */
public class WXSSException extends Exception {

	/**	 * 	 */
	private static final long serialVersionUID = 7971996009206502856L;
	
	private String code;

	private String desc;

	public WXSSException() { super(); }
	
	public WXSSException(String code, String desc) {
		this.setCode(code);
		this.setDesc(desc);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
