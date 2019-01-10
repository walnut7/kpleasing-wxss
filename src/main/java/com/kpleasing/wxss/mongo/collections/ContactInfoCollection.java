package com.kpleasing.wxss.mongo.collections;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="contact_info")
public class ContactInfoCollection implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -2461668592023955255L;
	private int userId;
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
	private String contact2Relation;
	private String contact2RelationCode;
	private String contact2Name;
	private String contact2CertType;
	private String contact2CertId;
	private String contact2Phone;
	private Date operateTime;
	private String operateType;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getContact2Relation() {
		return contact2Relation;
	}

	public void setContact2Relation(String contact2Relation) {
		this.contact2Relation = contact2Relation;
	}

	public String getContact2RelationCode() {
		return contact2RelationCode;
	}

	public void setContact2RelationCode(String contact2RelationCode) {
		this.contact2RelationCode = contact2RelationCode;
	}

	public String getContact2Name() {
		return contact2Name;
	}

	public void setContact2Name(String contact2Name) {
		this.contact2Name = contact2Name;
	}

	public String getContact2CertType() {
		return contact2CertType;
	}

	public void setContact2CertType(String contact2CertType) {
		this.contact2CertType = contact2CertType;
	}

	public String getContact2CertId() {
		return contact2CertId;
	}

	public void setContact2CertId(String contact2CertId) {
		this.contact2CertId = contact2CertId;
	}

	public String getContact2Phone() {
		return contact2Phone;
	}

	public void setContact2Phone(String contact2Phone) {
		this.contact2Phone = contact2Phone;
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
