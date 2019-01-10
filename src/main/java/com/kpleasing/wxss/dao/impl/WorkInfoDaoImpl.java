package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.WorkInfoDao;
import com.kpleasing.wxss.entity.WorkInfo;
import com.kpleasing.wxss.mongo.dao.WorkInfoCollectionDao;

@Repository("WorkInfoDao")
public class WorkInfoDaoImpl extends BaseDaoImpl<WorkInfo, Integer> implements WorkInfoDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 6167631022087802370L;
	private final static String USER_ID = "userId";
	
	@Autowired
	private WorkInfoCollectionDao workInfoCollectionDao;
	
	@Override
	public void save(WorkInfo workInfo) {
		hibernateTemplate.save(workInfo);
		workInfoCollectionDao.insertWorkInfoCollection(workInfo, "add");
	}
	
	@Override
	public void update(WorkInfo workInfo) {
		hibernateTemplate.update(workInfo);
		workInfoCollectionDao.insertWorkInfoCollection(workInfo, "modify");
	}
	
	@Override
	public void delete(WorkInfo workInfo) {
		hibernateTemplate.delete(workInfo);
		workInfoCollectionDao.insertWorkInfoCollection(workInfo, "delete");
	}

	@Override
	public WorkInfo findByUserId(Integer userId) {
		List<WorkInfo> list = this.findByProperty(USER_ID, userId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}
}
