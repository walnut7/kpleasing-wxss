package com.kpleasing.wxss.web.service;

import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.protocol.request.SyncCarInfoRequest;
import com.kpleasing.wxss.protocol.request.SyncFinSchemeRequest;
import com.kpleasing.wxss.protocol.request.SyncSPInfoRequest;

public interface APIService {

	/**
	 * 保存SP及销售信息
	 * @param syncSPInfoRequest
	 * @throws WXSSException
	 */
	public void saveSPAndSaleInfo(SyncSPInfoRequest syncSPInfoRequest) throws WXSSException;

	/**
	 * 保存车辆及参数信息
	 * @param syncCarInfoRequest
	 * @throws WXSSException
	 */
	public void saveCarAndParamInfo(SyncCarInfoRequest syncCarInfoRequest) throws WXSSException;

	/**
	 * 保存融资方案信息
	 * @param syncFinSchemeRequest
	 * @throws WXSSException
	 */
	public void saveFinSchemeInfo(SyncFinSchemeRequest syncFinSchemeRequest) throws WXSSException;
	
}
