package com.kpleasing.wxss.mongo.dao;

import com.kpleasing.wxss.entity.PersonInfo;

public interface PersonInfoCollectionDao {
	
	
	/**
	 * 
	 * @param person
	 * @param operType
	 */
	public void insertPersonInfoCollection(PersonInfo person, String operType);

}
