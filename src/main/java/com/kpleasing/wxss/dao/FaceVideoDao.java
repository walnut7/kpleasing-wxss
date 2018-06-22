package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.FaceVideo;

public interface FaceVideoDao extends BaseDao<FaceVideo, Integer> {
	
	/**
	 * 
	 * @param custId
	 * @return
	 */
	public FaceVideo findByCustId(Integer custId);
}
