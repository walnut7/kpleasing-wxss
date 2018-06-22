package com.kpleasing.wxss.esb.response;

import java.io.Serializable;

public class LEASING016Response extends ESBResponseHead implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -3877014198209030680L;
	private String result_code;
	private String result_desc;
	private String token;
	private String storable_pan;
	private String response_code;
	private String response_message;
	private String error_code;
	private String error_message;
	
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getStorable_pan() {
		return storable_pan;
	}

	public void setStorable_pan(String storable_pan) {
		this.storable_pan = storable_pan;
	}

	public String getResponse_code() {
		return response_code;
	}

	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}

	public String getResponse_message() {
		return response_message;
	}

	public void setResponse_message(String response_message) {
		this.response_message = response_message;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
}
