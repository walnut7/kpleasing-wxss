package com.kpleasing.wxss.esb.response;

import java.io.Serializable;

public class CRM007Response extends ESBResponseHead implements Serializable {
	/**	 * 	 */
	private static final long serialVersionUID = 1878504069662905773L;

	private String result_code;
	
	private String result_desc;

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getResult_desc() {
		return result_desc;
	}

	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
}
