package com.kpleasing.wxss.mongo.dao;

import com.kpleasing.wxss.entity.DrivingLicenseInfo;

public interface DrivingLicenseInfoCollectionDao {
	
	
	/**
	 * 
	 * @param driving
	 * @param operType
	 */
	 public void insertDrivingLicenseInfoCollection(DrivingLicenseInfo driving, String operType);

}
