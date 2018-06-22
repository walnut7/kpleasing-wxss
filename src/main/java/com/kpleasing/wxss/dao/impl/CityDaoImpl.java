package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.CityDao;
import com.kpleasing.wxss.entity.City;

@Repository("CityDao")
public class CityDaoImpl extends BaseDaoImpl<City, Integer> implements CityDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -3387776102823169568L;

}
