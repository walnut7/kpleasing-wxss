package com.kpleasing.wxss.mongo.dao.impl;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.mongo.collections.BankInfoCollection;
import com.kpleasing.wxss.mongo.dao.BankInfoCollectionDao;
import com.kpleasing.wxss.util.DateUtil;


@Repository("BankInfoCollectionDao")
public class BankInfoCollectionDaoImpl extends AbstractBaseMongoTemplete implements BankInfoCollectionDao {

	@Override
	public void insertBankInfoCollection(BankInfo bankInfo, String operType) {
		BankInfoCollection bankInfoCollection = new BankInfoCollection();
		bankInfoCollection.setUserId(bankInfo.getUserId());
		bankInfoCollection.setRepayAccBank(bankInfo.getRepayAccBank());
		bankInfoCollection.setRepayAccBankCode(bankInfo.getRepayAccBankCode());
		bankInfoCollection.setRepayCardNo(bankInfo.getRepayCardNo());
		bankInfoCollection.setBankPhone(bankInfo.getBankPhone());
		bankInfoCollection.setBankcardFrontImagePath(bankInfo.getBankcardFrontImagePath());
		bankInfoCollection.setBankcardBackImagePath(bankInfo.getBankcardBackImagePath());
		bankInfoCollection.setToken(bankInfo.getToken());
		bankInfoCollection.setStorablePan(bankInfo.getStorablePan());
		bankInfoCollection.setSmsSendtime(bankInfo.getSmsSendtime());
		bankInfoCollection.setExternalNo(bankInfo.getExternalNo());
		bankInfoCollection.setSpdbVerifyCode(bankInfo.getSpdbVerifyCode());
		bankInfoCollection.setSpdbFlag(bankInfo.getSpdbFlag());
		bankInfoCollection.setSpdbUuid(bankInfo.getSpdbUuid());
		bankInfoCollection.setSpdbAccountId(bankInfo.getSpdbAccountId());
		bankInfoCollection.setSpdbOpenAccountTime(bankInfo.getSpdbOpenAccountTime());
		bankInfoCollection.setSpdbAccountType(bankInfo.getSpdbAccountType());
		bankInfoCollection.setSpdbStcardNo(bankInfo.getSpdbStcardNo());
		bankInfoCollection.setOperateTime(DateUtil.getDate());
		bankInfoCollection.setOperateType(operType);
		
		mongoTemplate.save(bankInfoCollection);
	}
}
