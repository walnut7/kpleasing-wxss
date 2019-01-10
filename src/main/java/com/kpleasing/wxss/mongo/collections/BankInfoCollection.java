package com.kpleasing.wxss.mongo.collections;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="bank_info")
public class BankInfoCollection implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -7651871195426410224L;
	private Integer userId;
	private String repayAccBank;
	private String repayAccBankCode;
	private String repayCardNo;
	private String bankPhone;
	private String bankcardFrontImagePath;
	private String bankcardBackImagePath;
	private String token;
	private String storablePan;
	private String smsSendtime;
	private String externalNo;
	private String spdbVerifyCode;
	private byte spdbFlag;
	private String spdbUuid;
	private String spdbAccountId;
	private Date spdbOpenAccountTime;
	private String spdbAccountType;
	private String spdbStcardNo;
	private Date operateTime;
	private String operateType;
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRepayAccBank() {
		return repayAccBank;
	}

	public void setRepayAccBank(String repayAccBank) {
		this.repayAccBank = repayAccBank;
	}

	public String getRepayAccBankCode() {
		return repayAccBankCode;
	}

	public void setRepayAccBankCode(String repayAccBankCode) {
		this.repayAccBankCode = repayAccBankCode;
	}

	public String getRepayCardNo() {
		return repayCardNo;
	}

	public void setRepayCardNo(String repayCardNo) {
		this.repayCardNo = repayCardNo;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public String getBankcardFrontImagePath() {
		return bankcardFrontImagePath;
	}

	public void setBankcardFrontImagePath(String bankcardFrontImagePath) {
		this.bankcardFrontImagePath = bankcardFrontImagePath;
	}

	public String getBankcardBackImagePath() {
		return bankcardBackImagePath;
	}

	public void setBankcardBackImagePath(String bankcardBackImagePath) {
		this.bankcardBackImagePath = bankcardBackImagePath;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getStorablePan() {
		return storablePan;
	}

	public void setStorablePan(String storablePan) {
		this.storablePan = storablePan;
	}

	public String getSmsSendtime() {
		return smsSendtime;
	}

	public void setSmsSendtime(String smsSendtime) {
		this.smsSendtime = smsSendtime;
	}

	public String getExternalNo() {
		return externalNo;
	}

	public void setExternalNo(String externalNo) {
		this.externalNo = externalNo;
	}

	public String getSpdbVerifyCode() {
		return spdbVerifyCode;
	}

	public void setSpdbVerifyCode(String spdbVerifyCode) {
		this.spdbVerifyCode = spdbVerifyCode;
	}

	public byte getSpdbFlag() {
		return spdbFlag;
	}

	public void setSpdbFlag(byte spdbFlag) {
		this.spdbFlag = spdbFlag;
	}

	public String getSpdbUuid() {
		return spdbUuid;
	}

	public void setSpdbUuid(String spdbUuid) {
		this.spdbUuid = spdbUuid;
	}

	public String getSpdbAccountId() {
		return spdbAccountId;
	}

	public void setSpdbAccountId(String spdbAccountId) {
		this.spdbAccountId = spdbAccountId;
	}

	public Date getSpdbOpenAccountTime() {
		return spdbOpenAccountTime;
	}

	public void setSpdbOpenAccountTime(Date spdbOpenAccountTime) {
		this.spdbOpenAccountTime = spdbOpenAccountTime;
	}

	public String getSpdbAccountType() {
		return spdbAccountType;
	}

	public void setSpdbAccountType(String spdbAccountType) {
		this.spdbAccountType = spdbAccountType;
	}

	public String getSpdbStcardNo() {
		return spdbStcardNo;
	}

	public void setSpdbStcardNo(String spdbStcardNo) {
		this.spdbStcardNo = spdbStcardNo;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
}
