package com.kpleasing.wxss.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.dao.FaceVideoDao;
import com.kpleasing.wxss.entity.FaceVideo;

@Repository("FaceVideoDao")
public class FaceVideoDaoImpl extends BaseDaoImpl<FaceVideo, Integer> implements FaceVideoDao,Serializable {

	/**	 * 	 */
	private static final long serialVersionUID = -1931811348491544115L;
	private final static String CUST_ID = "custId";
	
	@Override
	public FaceVideo findByCustId(Integer custId) {
		List<FaceVideo> list = this.findByProperty(CUST_ID, custId);
		return (list!=null && list.size()>0)?list.get(0):null;
	}

}
