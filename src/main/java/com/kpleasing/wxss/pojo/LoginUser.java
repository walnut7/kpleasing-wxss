package com.kpleasing.wxss.pojo;

import java.io.Serializable;

public class LoginUser implements Serializable {
	
	/**	 * 	 */
	private static final long serialVersionUID = -6128054599583385278L;
	private String userId;
	private String userName;
	private String phone;
	private String certCode;
	private String openId;
	private String starttime;
	private String timeStamp;
	private String verifCode;
	private int loginType;
	private String sign;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getVerifCode() {
		return verifCode;
	}

	public void setVerifCode(String verifCode) {
		this.verifCode = verifCode;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getContentSign(String key) {
		StringBuilder sSign = new StringBuilder();
		sSign.append("cust_id=").append(this.getUserId())
		.append("&cert_code=").append(this.getCertCode())
		.append("&phone=").append(this.getPhone())
		.append("&time_stamp=").append(this.getTimeStamp())
		.append("&key=").append(key);
		return sSign.toString();
	}
}
