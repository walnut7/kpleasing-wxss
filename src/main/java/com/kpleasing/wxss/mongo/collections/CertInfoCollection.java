package com.kpleasing.wxss.mongo.collections;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="cert_info")
public class CertInfoCollection implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 4463853167922982035L;
	private int userId;
	private String userName;
	private String gender;
	private String genderCode;
	private String nation;
	private Date birthday;
	private String liveAddr;
	private String certType;
	private String certId;
	private String certAddr;
	private String certFrontImagePath;
	private String certBackImagePath;
	private Date operateTime;
	private String operateType;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getLiveAddr() {
		return liveAddr;
	}

	public void setLiveAddr(String liveAddr) {
		this.liveAddr = liveAddr;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getCertAddr() {
		return certAddr;
	}

	public void setCertAddr(String certAddr) {
		this.certAddr = certAddr;
	}

	public String getCertFrontImagePath() {
		return certFrontImagePath;
	}

	public void setCertFrontImagePath(String certFrontImagePath) {
		this.certFrontImagePath = certFrontImagePath;
	}

	public String getCertBackImagePath() {
		return certBackImagePath;
	}

	public void setCertBackImagePath(String certBackImagePath) {
		this.certBackImagePath = certBackImagePath;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
}
