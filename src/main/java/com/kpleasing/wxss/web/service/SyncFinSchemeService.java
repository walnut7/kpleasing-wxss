package com.kpleasing.wxss.web.service;

import com.kpleasing.wxss.entity.SecurityKey;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.protocol.request.SyncFinSchemeRequest;
import com.kpleasing.wxss.protocol.response.SyncFinSchemeResponse;

public interface SyncFinSchemeService {

	/**
	 * 生成响应XML报文
	 * @param syncFinSchemeRequest
	 * @param syncFinSchemeResp
	 * @return
	 */
	public String generateSyncFinSchemeResponseXML(SyncFinSchemeRequest syncFinSchemeRequest,SyncFinSchemeResponse syncFinSchemeResp,SecurityKey param) throws WXSSException;

	/**
	 * 
	 * @param code
	 * @param desc
	 * @return
	 */
	public String generateSyncFinSchemeErrorXML(String code, String desc);
}
