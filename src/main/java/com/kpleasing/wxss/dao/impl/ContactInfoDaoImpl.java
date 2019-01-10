package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.ContactInfoDao;
import com.kpleasing.wxss.entity.ContactInfo;
import com.kpleasing.wxss.mongo.dao.ContactInfoCollectionDao;

@Repository("ContactInfoDao")
public class ContactInfoDaoImpl extends BaseDaoImpl<ContactInfo, Integer> implements ContactInfoDao, Serializable  {

	/**	 * 	 */
	private static final long serialVersionUID = -756872387705814981L;
	private final static String USER_ID = "userId";
	
	@Autowired
	private ContactInfoCollectionDao contactInfoCollectionDao;
	
	@Override
	public void save(ContactInfo contactInfo) {
		hibernateTemplate.save(contactInfo);
		contactInfoCollectionDao.insertContactInfoCollection(contactInfo, "add");
	}
	
	@Override
	public void update(ContactInfo contactInfo) {
		hibernateTemplate.update(contactInfo);
		contactInfoCollectionDao.insertContactInfoCollection(contactInfo, "modify");
	}
	
	@Override
	public void delete(ContactInfo contactInfo) {
		hibernateTemplate.delete(contactInfo);
		contactInfoCollectionDao.insertContactInfoCollection(contactInfo, "delete");
	}

	@Override
	public ContactInfo findByUserId(Integer userId) {
		List<ContactInfo> list = this.findByProperty(USER_ID, userId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
