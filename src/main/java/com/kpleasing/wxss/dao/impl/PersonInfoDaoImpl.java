package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.PersonInfoDao;
import com.kpleasing.wxss.entity.PersonInfo;
import com.kpleasing.wxss.mongo.dao.PersonInfoCollectionDao;

@Repository("PersonInfoDao")
public class PersonInfoDaoImpl extends BaseDaoImpl<PersonInfo, Integer> implements PersonInfoDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 6326863850646783278L;
	private final static String USER_ID = "userId";
	
	@Autowired
	private PersonInfoCollectionDao personInfoCollectionDao;
	
	@Override
	public void save(PersonInfo personInfo) {
		hibernateTemplate.save(personInfo);
		personInfoCollectionDao.insertPersonInfoCollection(personInfo, "add");
	}
	
	@Override
	public void update(PersonInfo personInfo) {
		hibernateTemplate.update(personInfo);
		personInfoCollectionDao.insertPersonInfoCollection(personInfo, "modify");
	}
	
	@Override
	public void delete(PersonInfo personInfo) {
		hibernateTemplate.delete(personInfo);
		personInfoCollectionDao.insertPersonInfoCollection(personInfo, "delete");
	}

	@Override
	public PersonInfo findByUserId(Integer userId) {
		List<PersonInfo> list = this.findByProperty(USER_ID, userId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
