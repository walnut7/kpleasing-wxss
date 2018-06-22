package com.kpleasing.wxss.web.service;

import com.kpleasing.wxss.entity.SecurityKey;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.protocol.request.SyncCarInfoRequest;
import com.kpleasing.wxss.protocol.response.SyncCarInfoResponse;

public interface SyncCarInfoService {

	/**
	 * 生成响应XML报文
	 * @param syncCarInfoRequest
	 * @param syncCarInfoResp
	 * @return
	 */
	public String generateSyncCarInfoResponseXML(SyncCarInfoRequest syncCarInfoRequest,SyncCarInfoResponse syncCarInfoResp,SecurityKey param) throws WXSSException;

	/**
	 * 
	 * @param code
	 * @param desc
	 * @return
	 */
	public String generateSyncCarInfoErrorXML(String code, String desc);
}
