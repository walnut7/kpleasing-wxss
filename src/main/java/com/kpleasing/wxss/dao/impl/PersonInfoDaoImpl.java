package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.PersonInfoDao;
import com.kpleasing.wxss.entity.PersonInfo;

@Repository("PersonInfoDao")
public class PersonInfoDaoImpl extends BaseDaoImpl<PersonInfo, Integer> implements PersonInfoDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 6326863850646783278L;
	private final static String USER_ID = "userId";

	@Override
	public PersonInfo findByUserId(Integer userId) {
		List<PersonInfo> list = this.findByProperty(USER_ID, userId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
