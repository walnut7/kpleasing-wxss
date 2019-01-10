package com.kpleasing.wxss.mongo.dao.impl;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.entity.DrivingLicenseInfo;
import com.kpleasing.wxss.mongo.collections.DrivingLicenseInfoCollection;
import com.kpleasing.wxss.mongo.dao.DrivingLicenseInfoCollectionDao;
import com.kpleasing.wxss.util.DateUtil;

@Repository("DrivingLicenseInfoCollectionDao")
public class DrivingLicenseInfoCollectionDaoImpl extends AbstractBaseMongoTemplete implements DrivingLicenseInfoCollectionDao {

	@Override
	public void insertDrivingLicenseInfoCollection(DrivingLicenseInfo driving, String operType) {
		DrivingLicenseInfoCollection drivingLicenseInfoCollection = new DrivingLicenseInfoCollection();
		drivingLicenseInfoCollection.setUserId(driving.getUserId());
		drivingLicenseInfoCollection.setDriveType(driving.getDriveType());
		drivingLicenseInfoCollection.setDriveTypeDesc(driving.getDriveTypeDesc());
		drivingLicenseInfoCollection.setDriveFirstDate(driving.getDriveFirstDate());
		drivingLicenseInfoCollection.setDriveImagePath(driving.getDriveImagePath());
		drivingLicenseInfoCollection.setDriveBackImagePath(driving.getDriveBackImagePath());
		drivingLicenseInfoCollection.setOperateTime(DateUtil.getDate());
		drivingLicenseInfoCollection.setOperateType(operType);
		
		mongoTemplate.save(drivingLicenseInfoCollection);
		
	}
}
