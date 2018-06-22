package com.kpleasing.wxss.enums;

/**
 * 
 * @author howard.huang
 *
 */
public enum DEBUG {
	LEVEL1(1, "DEBUG_LEVEL1"),
	LEVEL2(2, "DEBUG_LEVEL2"),
	LEVEL3(3, "DEBUG_LEVEL3"),
	LEVEL4(4, "DEBUG_LEVEL4"),
	LEVEL5(5, "DEBUG_LEVEL5");
	
	public final int CODE;
	public final String DESCRIPTION;
	
	private DEBUG(int code, String desc) {
		this.CODE = code;
		this.DESCRIPTION = desc;
	}
	
	public static DEBUG valueOf(int code) {
		for (DEBUG s : values()) {
			if (s.CODE == code) {
				return s;
			}
		}
		return null;
	}
}
