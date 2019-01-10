package com.kpleasing.wxss.mongo.dao;

import com.kpleasing.wxss.entity.ContactInfo;

public interface ContactInfoCollectionDao {
	
	
	/**
	 * 
	 * @param person
	 * @param operType
	 */
	public void insertContactInfoCollection(ContactInfo contact, String operType);

}
