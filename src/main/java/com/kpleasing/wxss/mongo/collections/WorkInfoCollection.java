package com.kpleasing.wxss.mongo.collections;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="work_info")
public class WorkInfoCollection implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 5092150333001235039L;
	private int userId;
	private String workUnit;
	private String entryYear;
	private String position;
	private String unitTel;
	private String incomeStatus;
	private String incomeStatusCode;
	private String incomeFrom;
	private String incomeFromCode;
	private String annualIncome;
	private String workAddr;
	private String workYear;
	private Date operateTime;
	private String operateType;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getEntryYear() {
		return entryYear;
	}

	public void setEntryYear(String entryYear) {
		this.entryYear = entryYear;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUnitTel() {
		return unitTel;
	}

	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}

	public String getIncomeStatus() {
		return incomeStatus;
	}

	public void setIncomeStatus(String incomeStatus) {
		this.incomeStatus = incomeStatus;
	}

	public String getIncomeStatusCode() {
		return incomeStatusCode;
	}

	public void setIncomeStatusCode(String incomeStatusCode) {
		this.incomeStatusCode = incomeStatusCode;
	}

	public String getIncomeFrom() {
		return incomeFrom;
	}

	public void setIncomeFrom(String incomeFrom) {
		this.incomeFrom = incomeFrom;
	}

	public String getIncomeFromCode() {
		return incomeFromCode;
	}

	public void setIncomeFromCode(String incomeFromCode) {
		this.incomeFromCode = incomeFromCode;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getWorkAddr() {
		return workAddr;
	}

	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
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
