package com.kpleasing.wxss.web.service.impl;

import java.io.Serializable;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.dao.SpInfoDao;
import com.kpleasing.wxss.entity.SpInfo;
import com.kpleasing.wxss.web.service.SpBpInfoService;

/**
 * 处理SP相关信息
 * 
 * @author zhangxing
 *
 */
@Service("SpBpInfoService")
@Transactional
public class SpBpInfoServiceImpl implements Serializable, SpBpInfoService {

	/** * */
	private static final long serialVersionUID = 7535541948391979579L;
	private static final Logger logger = Logger.getLogger(SpBpInfoServiceImpl.class);
	
	@Autowired
	private SpInfoDao spInfoDao;

	/**
	 * 获取SP的基本信息
	 */
	@Override
	public SpInfo getSpBpInfo(Integer bpId, String bpCode) {
		SpInfo sp = spInfoDao.getSpBpInfo(bpId, bpCode);
		return sp;
	}
}
