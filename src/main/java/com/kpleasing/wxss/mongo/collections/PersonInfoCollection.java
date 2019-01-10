package com.kpleasing.wxss.mongo.collections;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="person_info")
public class PersonInfoCollection implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -2461668592023955255L;
	private int userId;
	private String userName;
	private String liveStatus;
	private String liveStatusCode;
	private String eduLevel;
	private String eduLevelCode;
	private String province;
	private String city;
	private String familyAddr;
	private String familyPhone;
	private String email;
	private String marrStatus;
	private String marrStatusCode;
	private String spouseName;
	private String spouseCertType;
	private String spouseCertId;
	private String spousePhone;
	private String spouseAnnualIncome;
	private String spouseAnnualIncomeCode;
	private String spouseIncomeFrom;
	private String spouseIncomeFromCode;
	private String spouseWorkUnit;
	private String contactRelation;
	private String contactRelationCode;
	private String contactName;
	private String contactCertType;
	private String contactCertId;
	private String contactPhone;
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

	public String getLiveStatus() {
		return liveStatus;
	}

	public void setLiveStatus(String liveStatus) {
		this.liveStatus = liveStatus;
	}

	public String getLiveStatusCode() {
		return liveStatusCode;
	}

	public void setLiveStatusCode(String liveStatusCode) {
		this.liveStatusCode = liveStatusCode;
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}

	public String getEduLevelCode() {
		return eduLevelCode;
	}

	public void setEduLevelCode(String eduLevelCode) {
		this.eduLevelCode = eduLevelCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFamilyAddr() {
		return familyAddr;
	}

	public void setFamilyAddr(String familyAddr) {
		this.familyAddr = familyAddr;
	}

	public String getFamilyPhone() {
		return familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMarrStatus() {
		return marrStatus;
	}

	public void setMarrStatus(String marrStatus) {
		this.marrStatus = marrStatus;
	}

	public String getMarrStatusCode() {
		return marrStatusCode;
	}

	public void setMarrStatusCode(String marrStatusCode) {
		this.marrStatusCode = marrStatusCode;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getSpouseCertType() {
		return spouseCertType;
	}

	public void setSpouseCertType(String spouseCertType) {
		this.spouseCertType = spouseCertType;
	}

	public String getSpouseCertId() {
		return spouseCertId;
	}

	public void setSpouseCertId(String spouseCertId) {
		this.spouseCertId = spouseCertId;
	}

	public String getSpousePhone() {
		return spousePhone;
	}

	public void setSpousePhone(String spousePhone) {
		this.spousePhone = spousePhone;
	}

	public String getSpouseAnnualIncome() {
		return spouseAnnualIncome;
	}

	public void setSpouseAnnualIncome(String spouseAnnualIncome) {
		this.spouseAnnualIncome = spouseAnnualIncome;
	}

	public String getSpouseAnnualIncomeCode() {
		return spouseAnnualIncomeCode;
	}

	public void setSpouseAnnualIncomeCode(String spouseAnnualIncomeCode) {
		this.spouseAnnualIncomeCode = spouseAnnualIncomeCode;
	}

	public String getSpouseIncomeFrom() {
		return spouseIncomeFrom;
	}

	public void setSpouseIncomeFrom(String spouseIncomeFrom) {
		this.spouseIncomeFrom = spouseIncomeFrom;
	}

	public String getSpouseIncomeFromCode() {
		return spouseIncomeFromCode;
	}

	public void setSpouseIncomeFromCode(String spouseIncomeFromCode) {
		this.spouseIncomeFromCode = spouseIncomeFromCode;
	}

	public String getSpouseWorkUnit() {
		return spouseWorkUnit;
	}

	public void setSpouseWorkUnit(String spouseWorkUnit) {
		this.spouseWorkUnit = spouseWorkUnit;
	}

	public String getContactRelation() {
		return contactRelation;
	}

	public void setContactRelation(String contactRelation) {
		this.contactRelation = contactRelation;
	}

	public String getContactRelationCode() {
		return contactRelationCode;
	}

	public void setContactRelationCode(String contactRelationCode) {
		this.contactRelationCode = contactRelationCode;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactCertType() {
		return contactCertType;
	}

	public void setContactCertType(String contactCertType) {
		this.contactCertType = contactCertType;
	}

	public String getContactCertId() {
		return contactCertId;
	}

	public void setContactCertId(String contactCertId) {
		this.contactCertId = contactCertId;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
