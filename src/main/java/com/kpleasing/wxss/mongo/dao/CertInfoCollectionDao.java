package com.kpleasing.wxss.mongo.dao;

import com.kpleasing.wxss.entity.CertInfo;

public interface CertInfoCollectionDao {
	
	/**
	 * 
	 * @param bankInfo
	 * @param operType
	 */
    public void insertCertInfoCollection(CertInfo certInfo, String operType);

}
