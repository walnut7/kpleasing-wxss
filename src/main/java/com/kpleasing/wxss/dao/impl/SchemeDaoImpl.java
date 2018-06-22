package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.SchemeDao;
import com.kpleasing.wxss.entity.Scheme;


@Repository("SchemeDao")
public class SchemeDaoImpl extends BaseDaoImpl<Scheme, Integer> implements SchemeDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -5079772225549561730L;

	@Override
	public Scheme findSchemeByPlanId(Integer planId) {
		List<Scheme> scheme_list = this.findByProperty("planId", planId);
		return (scheme_list!=null && scheme_list.size()>0)?scheme_list.get(0):null;
	}
	
	@Override
	public List<Scheme> getSchemeByModel(Integer bpId,String bpCode,Integer modelId) {
		String hql="select ts from Scheme  ts where exists ( from RCarScheme m,SpInfo s  where m.enabledFlag=0 and s.bpId=m.bpId and s.enabledFlag=0 and  s.bpId=? and s.bpCode=? and  m.planId = ts.planId ) and ts.modelId=? and SYSDATE() BETWEEN ts.validDateFrom   AND ts.validDateTo";
		Object[] values = new Object[] { bpId,bpCode,modelId };
		List<Scheme> scheme_list = this.find(hql, values);
		return scheme_list;
	}

}
