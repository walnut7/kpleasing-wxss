package com.kpleasing.wxss.enums;

/**
 * 
 * @author howard.huang
 *
 */
public enum LOGIN_TYPE {
	MANUAL(1, "MANUAL"),
	AUTO(2, "AUTO");
	
	public final int CODE;
	public final String DESCRIPTION;
	
	private LOGIN_TYPE(int code, String desc) {
		this.CODE = code;
		this.DESCRIPTION = desc;
	}
	
	public static LOGIN_TYPE valueOf(int code) {
		for (LOGIN_TYPE s : values()) {
			if (s.CODE == code) {
				return s;
			}
		}
		return null;
	}
}
