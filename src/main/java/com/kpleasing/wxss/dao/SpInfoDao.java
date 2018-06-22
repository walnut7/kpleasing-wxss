package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.SpInfo;

public interface SpInfoDao extends BaseDao<SpInfo, Integer> {
	
	public SpInfo getSpBpInfo(Integer bpId, String bpCode);

	public SpInfo findSpInfoByBpId(Integer bpId);
}
