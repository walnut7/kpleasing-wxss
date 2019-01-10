package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.ContactInfo;

public interface ContactInfoDao extends BaseDao<ContactInfo, Integer> {

	public ContactInfo findByUserId(Integer userId);

}
