package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.BankInfo;

public interface BankInfoDao extends BaseDao<BankInfo, Integer> {

	/**
	 * 根据用户ID查询用户银行账户信息
	 * @param userId
	 * @return
	 */
	public BankInfo findByUserId(Integer userId);

}
