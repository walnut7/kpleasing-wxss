package com.kpleasing.wxss.mongo.dao.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.mongo.collections.SpdbInterfaceLogCollection;
import com.kpleasing.wxss.mongo.dao.SpdbInterfaceLogCollectionDao;
import com.kpleasing.wxss.util.DateUtil;


@Repository("SpdbInterfaceLogCollectionDao")
public class SpdbInterfaceLogCollectionDaoImpl extends AbstractBaseMongoTemplete implements SpdbInterfaceLogCollectionDao {

	private static final Logger logger = Logger.getLogger(SpdbInterfaceLogCollectionDaoImpl.class);
	
	@Override
	public void insertSpdbInterfaceLogCollection(SpdbInterfaceLogCollection spdbInterfaceLog, String operType, String resultCode) {
		if("0000".equals(resultCode)) {
			spdbInterfaceLog.setResult("成功");
		} else {
			spdbInterfaceLog.setResult("失败");
		}
		spdbInterfaceLog.setOpType(operType);
		spdbInterfaceLog.setRequest_time(DateUtil.getDate());
		
		mongoTemplate.save(spdbInterfaceLog);
	}

	
	@Override
	public List<SpdbInterfaceLogCollection> searchSpdbOpertionLog(SpdbInterfaceLogCollection spdbInterfaceLogCollection) {
		Criteria criteria = new Criteria();
		if(null!=spdbInterfaceLogCollection.getCust_id()) {
			criteria.and("cust_id").is(spdbInterfaceLogCollection.getCust_id());
		}
		
		if(StringUtils.isNotBlank(spdbInterfaceLogCollection.getCust_name())) {
			criteria.and("cust_name").regex(spdbInterfaceLogCollection.getCust_name());
		}
		
		if(StringUtils.isNotBlank(spdbInterfaceLogCollection.getCert_code())) {
			criteria.and("cert_code").regex(spdbInterfaceLogCollection.getCert_code());
		}
		
		if(StringUtils.isNotBlank(spdbInterfaceLogCollection.getPhone())) {
			criteria.and("phone").regex(spdbInterfaceLogCollection.getPhone());
		}

		Query query = new Query();
		query.addCriteria(criteria).with(new Sort(Sort.Direction.DESC, "request_time"));
		return mongoTemplate.find(query, SpdbInterfaceLogCollection.class, "spdb_interface_log");
	}
}
