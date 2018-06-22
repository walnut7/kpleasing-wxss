package com.kpleasing.wxss.web.service;

import com.kpleasing.wxss.entity.SpInfo;

/**
 * 门店车辆信息查询
 * 
 * @author zhangxing
 *
 */
public interface SpBpInfoService {

	/**
	 * 根据bp_id,bp_code查询SP的基本信息
	 * 
	 * @param bp_id
	 * @param bp_code
	 * @return
	 */
	public SpInfo getSpBpInfo(Integer bpId, String bpCode);

}
