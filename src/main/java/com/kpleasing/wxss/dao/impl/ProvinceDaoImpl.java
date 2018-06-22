package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.ProvinceDao;
import com.kpleasing.wxss.entity.Province;


@Repository("ProvinceDao")
public class ProvinceDaoImpl extends BaseDaoImpl<Province, Integer> implements ProvinceDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 9124434503602405320L;

}
