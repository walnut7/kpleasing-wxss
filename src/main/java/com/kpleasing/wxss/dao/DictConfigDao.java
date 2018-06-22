package com.kpleasing.wxss.dao;


import java.util.List;

import com.kpleasing.wxss.entity.DictConfig;

public interface DictConfigDao extends BaseDao<DictConfig, Integer>  {
	
	public DictConfig findDictByNodeCode(String nodeCode);

	
	/**
	 * 
	 * @param string
	 * @param pId
	 * @return
	 */
	public List<DictConfig> findDictByParentNodeCode(Integer pId);

}
