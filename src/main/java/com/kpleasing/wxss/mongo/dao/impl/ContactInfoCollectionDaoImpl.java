package com.kpleasing.wxss.mongo.dao.impl;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.entity.ContactInfo;
import com.kpleasing.wxss.mongo.collections.ContactInfoCollection;
import com.kpleasing.wxss.mongo.dao.ContactInfoCollectionDao;
import com.kpleasing.wxss.util.DateUtil;


@Repository("ContactInfoCollectionDao")
public class ContactInfoCollectionDaoImpl extends AbstractBaseMongoTemplete implements ContactInfoCollectionDao {

	@Override
	public void insertContactInfoCollection(ContactInfo contact, String operType) {
		ContactInfoCollection contactInfoCollection = new ContactInfoCollection();
		contactInfoCollection.setUserId(contact.getUserId());
		contactInfoCollection.setSpouseName(contact.getSpouseName());
		contactInfoCollection.setSpouseCertType(contact.getSpouseCertType());
		contactInfoCollection.setSpouseCertId(contact.getSpouseCertId());
		contactInfoCollection.setSpousePhone(contact.getSpousePhone());
		contactInfoCollection.setSpouseAnnualIncome(contact.getSpouseAnnualIncome());
		contactInfoCollection.setSpouseAnnualIncomeCode(contact.getSpouseAnnualIncomeCode());
		contactInfoCollection.setSpouseIncomeFrom(contact.getSpouseIncomeFrom());
		contactInfoCollection.setSpouseIncomeFromCode(contact.getSpouseIncomeFromCode());
		contactInfoCollection.setSpouseWorkUnit(contact.getSpouseWorkUnit());
		contactInfoCollection.setContactRelation(contact.getContactRelation());
		contactInfoCollection.setContactRelationCode(contact.getContactRelationCode());
		contactInfoCollection.setContactName(contact.getContactName());
		contactInfoCollection.setContactCertType(contact.getContactCertType());
		contactInfoCollection.setContactCertId(contact.getContactCertId());
		contactInfoCollection.setContactPhone(contact.getContactPhone());
		contactInfoCollection.setContact2Relation(contact.getContact2Relation());
		contactInfoCollection.setContact2RelationCode(contact.getContact2RelationCode());
		contactInfoCollection.setContact2Name(contact.getContact2Name());
		contactInfoCollection.setContact2CertType(contact.getContact2CertType());
		contactInfoCollection.setContact2CertId(contact.getContact2CertId());
		contactInfoCollection.setContact2Phone(contact.getContact2Phone());
		contactInfoCollection.setOperateTime(DateUtil.getDate());
		contactInfoCollection.setOperateType(operType);
		
		mongoTemplate.save(contactInfoCollection);
	}
}
