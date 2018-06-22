package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.CertInfo;

public interface CertInfoDao extends BaseDao<CertInfo, Integer> {
	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	public CertInfo findByUserId(Integer userId);

}
