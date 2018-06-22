package com.kpleasing.wxss.exception;

public class InputParamException extends WXSSException {

	/**	 * 	 */
	private static final long serialVersionUID = 5102134893080575662L;

	
	

	private String req_serial_no;
	
	private String req_date;
	
	public InputParamException(String desc, String req_serial_no, String req_date) {
		super.setCode("FAILED");
		super.setDesc(desc);
		this.setReq_serial_no(req_serial_no);
		this.setReq_date(req_date);
	}
	
	public String getReq_serial_no() {
		return req_serial_no;
	}

	public void setReq_serial_no(String req_serial_no) {
		this.req_serial_no = req_serial_no;
	}

	public String getReq_date() {
		return req_date;
	}

	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
}
