package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.DrivingLicenseInfo;

public interface DrivingLicenseInfoDao extends BaseDao<DrivingLicenseInfo, Integer> {

	
	/**
	 * 根据用户ID 查询驾照信息
	 * @param userId
	 * @return
	 */
	public DrivingLicenseInfo findByUserId(Integer userId);

}
