package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.SecurityKeyDao;
import com.kpleasing.wxss.entity.SecurityKey;


@Repository("SecurityKeyDao")
public class SecurityKeyDaoImpl extends BaseDaoImpl<SecurityKey, Integer> implements SecurityKeyDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 6173125217085315618L;

}
