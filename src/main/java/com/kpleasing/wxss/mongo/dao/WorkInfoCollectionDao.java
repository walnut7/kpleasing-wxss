package com.kpleasing.wxss.mongo.dao;

import com.kpleasing.wxss.entity.WorkInfo;

public interface WorkInfoCollectionDao {

	
	/**
	 * 
	 * @param work
	 * @param operType
	 */
	public void insertWorkInfoCollection(WorkInfo work, String operType);
}
