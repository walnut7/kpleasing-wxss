package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.BankInfoDao;
import com.kpleasing.wxss.entity.BankInfo;


@Repository("BankInfoDao")
public class BankInfoDaoImpl extends BaseDaoImpl<BankInfo, Integer> implements BankInfoDao, Serializable {

	/**	 *  */
	private static final long serialVersionUID = 2813303388816509402L;
	private final static String USER_ID = "userId";
	
	@Override
	public BankInfo findByUserId(Integer userId) {
		List<BankInfo> list = this.findByProperty(USER_ID, userId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

}
