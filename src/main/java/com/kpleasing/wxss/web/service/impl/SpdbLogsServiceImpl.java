package com.kpleasing.wxss.web.service.impl;

import java.io.Serializable;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.mongo.collections.SpdbInterfaceLogCollection;
import com.kpleasing.wxss.mongo.dao.SpdbInterfaceLogCollectionDao;
import com.kpleasing.wxss.web.service.SpdbLogsService;


@Service("SpdbLogsService")
@Transactional
public class SpdbLogsServiceImpl implements Serializable, SpdbLogsService {

	/**	 * 	 */
	private static final long serialVersionUID = 325742919781455408L;
	private static final Logger logger = Logger.getLogger(SpdbLogsServiceImpl.class);
	
	@Autowired
	private SpdbInterfaceLogCollectionDao spdbLogDao;

	@Override
	public List<SpdbInterfaceLogCollection> findSpdbOperateLog(SpdbInterfaceLogCollection spdbInterfaceLogCollection) {
		return spdbLogDao.searchSpdbOpertionLog(spdbInterfaceLogCollection);
	}
}
