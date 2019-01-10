package com.kpleasing.wxss.mongo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.mongo.collections.OrderCollection;
import com.kpleasing.wxss.mongo.dao.OrderCollectionDao;
import com.kpleasing.wxss.util.DateUtil;


@Repository("OrderCollectionDao")
public class OrderDaoCollectionImpl extends AbstractBaseMongoTemplete implements OrderCollectionDao {

	@Override
	public List<OrderCollection> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void insertOrderCollection(Order order, String operType) {
		OrderCollection orderCollection = new OrderCollection();
		orderCollection.setOrderId(order.getOrderId());
		orderCollection.setBpId(order.getBpId());
		orderCollection.setBpCode(order.getBpCode());
		orderCollection.setPlanId(order.getPlanId());
		orderCollection.setSaleId(order.getSaleId());
		orderCollection.setCustId(order.getCustId());
		orderCollection.setUserName(order.getUserName());
		orderCollection.setPhone(order.getPhone());
		orderCollection.setCertType(order.getCertType());
		orderCollection.setCertId(order.getCertId());
		orderCollection.setWxOpenId(order.getWxOpenId());
		orderCollection.setStepNo(order.getStepNo());
		orderCollection.setStatus(order.getStatus());
		orderCollection.setLoginChannel(order.getLoginChannel());
		orderCollection.setCreateAt(order.getCreateAt());
		orderCollection.setUpdateAt(order.getUpdateAt());
		orderCollection.setRemark(order.getRemark());
		orderCollection.setOperateTime(DateUtil.getDate());
		orderCollection.setOperateType(operType);
		
		mongoTemplate.save(orderCollection);
	}
}
