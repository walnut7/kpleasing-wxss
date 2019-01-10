package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.CertInfoDao;
import com.kpleasing.wxss.entity.CertInfo;
import com.kpleasing.wxss.mongo.dao.CertInfoCollectionDao;


@Repository("CertInfoDao")
public class CertInfoDaoImpl extends BaseDaoImpl<CertInfo, Integer> implements CertInfoDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -2913613403400557542L;
	private final static String USER_ID = "userId";

	
	@Autowired
	private CertInfoCollectionDao certInfoCollectionDao;
	
	
	@Override
	public void save(CertInfo certInfo) {
		hibernateTemplate.save(certInfo);
		certInfoCollectionDao.insertCertInfoCollection(certInfo, "add");
	}
	
	@Override
	public void update(CertInfo certInfo) {
		hibernateTemplate.update(certInfo);
		certInfoCollectionDao.insertCertInfoCollection(certInfo, "modify");
	}
	
	@Override
	public void delete(CertInfo certInfo) {
		hibernateTemplate.delete(certInfo);
		certInfoCollectionDao.insertCertInfoCollection(certInfo, "delete");
	}
	
	@Override
	public CertInfo findByUserId(Integer userId) {
		List<CertInfo> list = this.findByProperty(USER_ID, userId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

}
