package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.WorkInfo;

public interface WorkInfoDao extends BaseDao<WorkInfo, Integer> {

	public WorkInfo findByUserId(Integer userId);

}
