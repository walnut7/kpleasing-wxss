package com.kpleasing.wxss.mongo.dao.impl;


import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.entity.CertInfo;
import com.kpleasing.wxss.mongo.collections.CertInfoCollection;
import com.kpleasing.wxss.mongo.dao.CertInfoCollectionDao;
import com.kpleasing.wxss.util.DateUtil;

@Repository("CertInfoCollectionDao")
public class CertInfoCollectionDaoImpl extends AbstractBaseMongoTemplete implements CertInfoCollectionDao {

	@Override
	public void insertCertInfoCollection(CertInfo certInfo, String operType) {
		CertInfoCollection certInfoCollection = new CertInfoCollection();
		certInfoCollection.setUserId(certInfo.getUserId());
		certInfoCollection.setUserName(certInfo.getUserName());
		certInfoCollection.setGender(certInfo.getGender());
		certInfoCollection.setGenderCode(certInfo.getGenderCode());
		certInfoCollection.setNation(certInfo.getNation());
		certInfoCollection.setBirthday(certInfo.getBirthday());
		certInfoCollection.setLiveAddr(certInfo.getLiveAddr());
		certInfoCollection.setCertType(certInfo.getCertType());
		certInfoCollection.setCertId(certInfo.getCertId());
		certInfoCollection.setCertAddr(certInfo.getCertAddr());
		certInfoCollection.setCertFrontImagePath(certInfo.getCertFrontImagePath());
		certInfoCollection.setCertBackImagePath(certInfo.getCertBackImagePath());
		certInfoCollection.setOperateTime(DateUtil.getDate());
		certInfoCollection.setOperateType(operType);
		
		mongoTemplate.save(certInfoCollection);
	}
}
