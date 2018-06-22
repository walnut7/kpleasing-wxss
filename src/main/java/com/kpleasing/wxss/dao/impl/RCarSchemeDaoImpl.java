package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.RCarSchemeDao;
import com.kpleasing.wxss.entity.RCarScheme;

@Repository("RCarSchemeDao")
public class RCarSchemeDaoImpl extends BaseDaoImpl<RCarScheme, Integer> implements RCarSchemeDao,Serializable {

	/** *  */
	private static final long serialVersionUID = 767071205332987810L;

	@Override
	public RCarScheme findRCarSchemeByPropertys(Integer planId, Integer bpId) {
		String[] propertyNames= {"planId","bpId"};
		Object[] values = {planId,bpId};
		List<RCarScheme> rcs_list = this.findByPropertys(propertyNames, values);
		return (rcs_list!=null && rcs_list.size()>0)?rcs_list.get(0):null;
	}

}
