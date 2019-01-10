package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.DrivingLicenseInfoDao;
import com.kpleasing.wxss.entity.DrivingLicenseInfo;
import com.kpleasing.wxss.mongo.dao.DrivingLicenseInfoCollectionDao;


@Repository("DrivingLicenseInfoDao")
public class DrivingLicenseInfoDaoImpl extends BaseDaoImpl<DrivingLicenseInfo, Integer> implements DrivingLicenseInfoDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 3483574242831809111L;
	private final static String USER_ID = "userId";
	
	@Autowired
	private DrivingLicenseInfoCollectionDao drivingLicenseInfoCollectionDao;
	
	@Override
	public void save(DrivingLicenseInfo driving) {
		hibernateTemplate.save(driving);
		drivingLicenseInfoCollectionDao.insertDrivingLicenseInfoCollection(driving, "add");
	}
	
	@Override
	public void update(DrivingLicenseInfo driving) {
		hibernateTemplate.update(driving);
		drivingLicenseInfoCollectionDao.insertDrivingLicenseInfoCollection(driving, "modify");
	}
	
	@Override
	public void delete(DrivingLicenseInfo driving) {
		hibernateTemplate.delete(driving);
		drivingLicenseInfoCollectionDao.insertDrivingLicenseInfoCollection(driving, "delete");
	}

	@Override
	public DrivingLicenseInfo findByUserId(Integer userId) {
		List<DrivingLicenseInfo> list = this.findByProperty(USER_ID, userId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
