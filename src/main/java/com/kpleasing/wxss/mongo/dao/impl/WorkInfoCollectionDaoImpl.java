package com.kpleasing.wxss.mongo.dao.impl;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.entity.WorkInfo;
import com.kpleasing.wxss.mongo.collections.WorkInfoCollection;
import com.kpleasing.wxss.mongo.dao.WorkInfoCollectionDao;
import com.kpleasing.wxss.util.DateUtil;

@Repository("WorkInfoCollectionDao")
public class WorkInfoCollectionDaoImpl extends AbstractBaseMongoTemplete implements WorkInfoCollectionDao  {

	@Override
	public void insertWorkInfoCollection(WorkInfo work, String operType) {
		WorkInfoCollection workInfoCollection = new WorkInfoCollection();
		workInfoCollection.setUserId(work.getUserId());
		workInfoCollection.setWorkUnit(work.getWorkUnit());
		workInfoCollection.setEntryYear(work.getEntryYear());
		workInfoCollection.setPosition(work.getPosition());
		workInfoCollection.setUnitTel(work.getUnitTel());
		workInfoCollection.setIncomeStatus(work.getIncomeStatus());
		workInfoCollection.setIncomeStatusCode(work.getIncomeStatusCode());
		workInfoCollection.setIncomeFrom(work.getIncomeFrom());
		workInfoCollection.setIncomeFromCode(work.getIncomeFromCode());
		workInfoCollection.setAnnualIncome(work.getAnnualIncome());
		workInfoCollection.setWorkAddr(work.getWorkAddr());
		workInfoCollection.setWorkYear(work.getWorkYear());
		workInfoCollection.setOperateTime(DateUtil.getDate());
		workInfoCollection.setOperateType(operType);
		
		mongoTemplate.save(workInfoCollection);
	}
}
