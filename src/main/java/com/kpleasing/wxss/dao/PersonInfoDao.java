package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.PersonInfo;

public interface PersonInfoDao extends BaseDao<PersonInfo, Integer> {

	public PersonInfo findByUserId(Integer userId);

}
