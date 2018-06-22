package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.ConfigDao;
import com.kpleasing.wxss.entity.Config;

@Repository("ConfigDao")
public class ConfigDaoImpl extends BaseDaoImpl<Config, Integer> implements ConfigDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -8353482328848546022L;

}
