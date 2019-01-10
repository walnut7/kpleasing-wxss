package com.kpleasing.wxss.mongo.collections;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="driving_license_info")
public class DrivingLicenseInfoCollection implements Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -6342125691586619382L;
	private int userId;
	private String driveType;
	private String driveTypeDesc;
	private Date driveFirstDate;
	private String driveImagePath;
	private String driveBackImagePath;
	private Date operateTime;
	private String operateType;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDriveType() {
		return driveType;
	}

	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}

	public String getDriveTypeDesc() {
		return driveTypeDesc;
	}

	public void setDriveTypeDesc(String driveTypeDesc) {
		this.driveTypeDesc = driveTypeDesc;
	}

	public Date getDriveFirstDate() {
		return driveFirstDate;
	}

	public void setDriveFirstDate(Date driveFirstDate) {
		this.driveFirstDate = driveFirstDate;
	}

	public String getDriveImagePath() {
		return driveImagePath;
	}

	public void setDriveImagePath(String driveImagePath) {
		this.driveImagePath = driveImagePath;
	}

	public String getDriveBackImagePath() {
		return driveBackImagePath;
	}

	public void setDriveBackImagePath(String driveBackImagePath) {
		this.driveBackImagePath = driveBackImagePath;
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
