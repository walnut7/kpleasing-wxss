package com.kpleasing.wxss.web.service;

import com.kpleasing.wxss.entity.SecurityKey;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.protocol.request.SyncSPInfoRequest;
import com.kpleasing.wxss.protocol.response.SyncSPInfoResponse;

public interface SyncSPInfoService {
    
	/**
	 * 生成响应XML报文
	 * @param syncSPInfoRequest
	 * @param syncSPInfoResp
	 * @return
	 */
	public String generateSyncSPInfoResponseXML(SyncSPInfoRequest syncSPInfoRequest,SyncSPInfoResponse syncSPInfoResp,SecurityKey param) throws WXSSException;

	/**
	 * 
	 * @param code
	 * @param desc
	 * @return
	 */
	public String generateSyncSPInfoErrorXML(String code, String desc);

}
