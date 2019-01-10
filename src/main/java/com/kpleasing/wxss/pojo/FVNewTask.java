package com.kpleasing.wxss.pojo;

import java.io.Serializable;

import com.kpleasing.wxss.util.Security;


public class FVNewTask implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -5473953813265368557L;
	private String action;
	private String bizCode;
	private String tm;
	private String random;
	private String sign;
	private String appCode;
	private String bizId;
	private String custName;
	private String custMobile;
	private String custIdcardNbr;
	private String bizInfo;
	private String comment;
	private String isRecordOn;
	private String storageYear;
	private String isScreenShotOn;
	private String isAiOn;
	private String isFaceCheckOn;
	private String isOcrOn;
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getTm() {
		return tm;
	}

	public void setTm(String tm) {
		this.tm = tm;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getCustIdcardNbr() {
		return custIdcardNbr;
	}

	public void setCustIdcardNbr(String custIdcardNbr) {
		this.custIdcardNbr = custIdcardNbr;
	}

	public String getBizInfo() {
		return bizInfo;
	}

	public void setBizInfo(String bizInfo) {
		this.bizInfo = bizInfo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIsRecordOn() {
		return isRecordOn;
	}

	public void setIsRecordOn(String isRecordOn) {
		this.isRecordOn = isRecordOn;
	}

	public String getStorageYear() {
		return storageYear;
	}

	public void setStorageYear(String storageYear) {
		this.storageYear = storageYear;
	}

	public String getIsScreenShotOn() {
		return isScreenShotOn;
	}

	public void setIsScreenShotOn(String isScreenShotOn) {
		this.isScreenShotOn = isScreenShotOn;
	}

	public String getIsAiOn() {
		return isAiOn;
	}

	public void setIsAiOn(String isAiOn) {
		this.isAiOn = isAiOn;
	}

	public String getIsFaceCheckOn() {
		return isFaceCheckOn;
	}

	public void setIsFaceCheckOn(String isFaceCheckOn) {
		this.isFaceCheckOn = isFaceCheckOn;
	}

	public String getIsOcrOn() {
		return isOcrOn;
	}

	public void setIsOcrOn(String isOcrOn) {
		this.isOcrOn = isOcrOn;
	}
	
	public String MD5Sign(String key) {
		return Security.MD5Encode(this.bizCode+this.tm+key+this.random).toUpperCase();
	}
}
