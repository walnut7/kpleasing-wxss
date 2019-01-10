package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.BankInfoDao;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.mongo.dao.BankInfoCollectionDao;


@Repository("BankInfoDao")
public class BankInfoDaoImpl extends BaseDaoImpl<BankInfo, Integer> implements BankInfoDao, Serializable {

	/**	 *  */
	private static final long serialVersionUID = 2813303388816509402L;
	private final static String USER_ID = "userId";
	
	
	@Autowired
	private BankInfoCollectionDao bankInfoCollectionDao;
	
	
	@Override
	public void save(BankInfo bankInfo) {
		hibernateTemplate.save(bankInfo);
		bankInfoCollectionDao.insertBankInfoCollection(bankInfo, "add");
	}
	
	@Override
	public void update(BankInfo bankInfo) {
		hibernateTemplate.update(bankInfo);
		bankInfoCollectionDao.insertBankInfoCollection(bankInfo, "modify");
	}
	
	@Override
	public void delete(BankInfo bankInfo) {
		hibernateTemplate.delete(bankInfo);
		bankInfoCollectionDao.insertBankInfoCollection(bankInfo, "delete");
	}
	
	@Override
	public BankInfo findByUserId(Integer userId) {
		List<BankInfo> list = this.findByProperty(USER_ID, userId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

}
