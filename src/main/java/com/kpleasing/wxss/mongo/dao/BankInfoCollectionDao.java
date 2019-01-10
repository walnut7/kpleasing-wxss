package com.kpleasing.wxss.mongo.dao;

import com.kpleasing.wxss.entity.BankInfo;

public interface BankInfoCollectionDao {
	
	/**
	 * 
	 * @param bankInfo
	 * @param operType
	 */
    public void insertBankInfoCollection(BankInfo bankInfo, String operType);


}
