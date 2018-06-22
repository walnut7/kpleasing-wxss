package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.SpInfoDao;
import com.kpleasing.wxss.entity.SpInfo;

@Repository("SpInfoDao")
public  class SpInfoDaoImpl extends BaseDaoImpl<SpInfo, Integer> implements SpInfoDao, Serializable {

	/** * */
	private static final long serialVersionUID = -5420771144606209597L;

	
	public SpInfo getSpBpInfo(Integer bp_id, String bp_code) {
		//logger.info("-----调用数据库------"+bp_id+","+bp_code);
		String[] propertyNames = {"bpId","bpCode"};
		Object[] valueObj = {bp_id,bp_code};
		List<SpInfo> list = this.findByPropertys(propertyNames, valueObj);
		//logger.info("-----数据库调用成功------"+list.toString());
		return (list != null && list.size() == 1) ? list.get(0) : null;
	}


	@Override
	public SpInfo findSpInfoByBpId(Integer bpId) {
		List<SpInfo> splist = this.findByProperty("bpId", bpId);
		return (splist!=null && splist.size()>0)?splist.get(0):null;
	}
	
}
