package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.CarParamDao;
import com.kpleasing.wxss.entity.CarParam;


@Repository("CarParamDao")
public class CarParamDaoImpl extends BaseDaoImpl<CarParam, Integer> implements CarParamDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 4151170660395214218L;

	@Override
	public CarParam findCarParamByPoropertys(Integer modelId, String param_name) {
		String[] propertyNames= {"modelId","modelParamName"};
		Object[] values = {modelId,param_name};
		List<CarParam> cp_list = this.findByPropertys(propertyNames, values);
		return (cp_list!=null && cp_list.size()>0)?cp_list.get(0):null;
	}

}
