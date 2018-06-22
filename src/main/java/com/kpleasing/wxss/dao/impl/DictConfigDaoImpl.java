package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.DictConfigDao;
import com.kpleasing.wxss.entity.DictConfig;


@Repository("DictConfigDao")
public class DictConfigDaoImpl extends BaseDaoImpl<DictConfig, Integer> implements DictConfigDao, Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = 8602954889036769747L;
	private final static String PARENT_NODE_ID = "parentNodeId";
	private final static String NODE_CODE = "nodeCode";
	private final static String STATUS = "status";
	private final static String DEL_FLAG = "delFlag";
	
	@Override
	public DictConfig findDictByNodeCode(String nodeCode) {
		List<DictConfig> list = this.findByPropertys(new String[] {NODE_CODE, STATUS, DEL_FLAG}, new Object[] {nodeCode, (byte)0, (byte)0});
		return (list!=null && list.size()>0)?list.get(0):null;
	}

	
	@Override
	public List<DictConfig> findDictByParentNodeCode(Integer pId) {
		return this.findByPropertys(new String[] {PARENT_NODE_ID, STATUS, DEL_FLAG}, new Object[] {pId, (byte)0, (byte)0});
	}
}
