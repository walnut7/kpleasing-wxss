package com.kpleasing.wxss.web.service.impl;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.mongo.collections.OrderCollection;
import com.kpleasing.wxss.mongo.dao.OrderCollectionDao;
import com.kpleasing.wxss.web.service.DemoService;


@Service("DemoService")
@Transactional
public class DemoServiceImpl implements DemoService {
	
	private static final Logger logger = Logger.getLogger(DemoServiceImpl.class);
	
	@Autowired
	private OrderCollectionDao orderCollectionDao;

	@Override
	public void testMongoInsert() {
		logger.info("start mongodb service implements ......");
		Order orderCollection = new Order();
		orderCollection.setBpCode("123456");
		orderCollectionDao.insertOrderCollection(orderCollection, "add");
		List<OrderCollection> OrderCollections = orderCollectionDao.findAll();
		if(OrderCollections.isEmpty()) logger.info("查无数据。。。。");
		for(OrderCollection order : OrderCollections) {
			 logger.info(order.getUserName()+"::"+orderCollection.getBpCode());
		}
	}
}
