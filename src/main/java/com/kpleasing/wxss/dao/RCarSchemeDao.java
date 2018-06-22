package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.RCarScheme;

public interface RCarSchemeDao extends BaseDao<RCarScheme, Integer> {

	public RCarScheme findRCarSchemeByPropertys(Integer planId, Integer bpId);

}
