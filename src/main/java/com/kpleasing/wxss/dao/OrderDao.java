package com.kpleasing.wxss.dao;

import com.kpleasing.wxss.entity.Order;

public interface OrderDao extends BaseDao<Order, Integer> {

	/**
	 * 
	 * @param custId
	 * @return
	 */
	public Order findByCustId(Integer custId);

}
