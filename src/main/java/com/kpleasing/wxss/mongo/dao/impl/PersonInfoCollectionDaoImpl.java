package com.kpleasing.wxss.mongo.dao.impl;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.entity.PersonInfo;
import com.kpleasing.wxss.mongo.collections.PersonInfoCollection;
import com.kpleasing.wxss.mongo.dao.PersonInfoCollectionDao;
import com.kpleasing.wxss.util.DateUtil;


@Repository("PersonInfoCollectionDao")
public class PersonInfoCollectionDaoImpl extends AbstractBaseMongoTemplete implements PersonInfoCollectionDao {

	@Override
	public void insertPersonInfoCollection(PersonInfo person, String operType) {
		PersonInfoCollection personInfoCollection = new PersonInfoCollection();
		personInfoCollection.setUserId(person.getUserId());
		personInfoCollection.setUserName(person.getUserName());
		personInfoCollection.setLiveStatus(person.getLiveStatus());
		personInfoCollection.setLiveStatusCode(person.getLiveStatusCode());
		personInfoCollection.setEduLevel(person.getEduLevel());
		personInfoCollection.setEduLevelCode(person.getEduLevelCode());
		personInfoCollection.setProvince(person.getProvince());
		personInfoCollection.setCity(person.getCity());
		personInfoCollection.setFamilyAddr(person.getFamilyAddr());
		personInfoCollection.setFamilyPhone(person.getFamilyPhone());
		personInfoCollection.setEmail(person.getEmail());
		personInfoCollection.setMarrStatus(person.getMarrStatus());
		personInfoCollection.setMarrStatusCode(person.getMarrStatusCode());
		personInfoCollection.setSpouseName(person.getSpouseName());
		personInfoCollection.setSpouseCertType(person.getSpouseCertType());
		personInfoCollection.setSpouseCertId(person.getSpouseCertId());
		personInfoCollection.setSpousePhone(person.getSpousePhone());
		personInfoCollection.setSpouseAnnualIncome(person.getSpouseAnnualIncome());
		personInfoCollection.setSpouseAnnualIncomeCode(person.getSpouseAnnualIncomeCode());
		personInfoCollection.setSpouseIncomeFrom(person.getSpouseIncomeFrom());
		personInfoCollection.setSpouseIncomeFromCode(person.getSpouseIncomeFromCode());
		personInfoCollection.setSpouseWorkUnit(person.getSpouseWorkUnit());
		personInfoCollection.setContactRelation(person.getContactRelation());
		personInfoCollection.setContactRelationCode(person.getContactRelationCode());
		personInfoCollection.setContactName(person.getContactName());
		personInfoCollection.setContactCertType(person.getContactCertType());
		personInfoCollection.setContactCertId(person.getContactCertId());
		personInfoCollection.setContactPhone(person.getContactPhone());
		personInfoCollection.setOperateTime(DateUtil.getDate());
		personInfoCollection.setOperateType(operType);
		
		mongoTemplate.save(personInfoCollection);
	}
}
