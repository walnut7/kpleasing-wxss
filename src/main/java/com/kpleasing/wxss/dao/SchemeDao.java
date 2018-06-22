package com.kpleasing.wxss.dao;

import java.util.List;

import com.kpleasing.wxss.entity.Scheme;

public interface SchemeDao extends BaseDao<Scheme, Integer> {

	public Scheme findSchemeByPlanId(Integer planId);
	
	public List<Scheme> getSchemeByModel(Integer bpId,String bpCode,Integer modelId);
}
