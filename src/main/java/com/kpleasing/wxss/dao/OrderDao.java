package com.kpleasing.wxss.dao;


import java.util.List;

import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.pojo.SearchParam;

public interface OrderDao extends BaseDao<Order, Integer> {

	/**
	 * 
	 * @param custId
	 * @return
	 */
	public Order findByCustId(Integer custId);


	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public List<Order> findOrderSortByUpdate(SearchParam parameter);


	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public int countTotalOrders(SearchParam parameter);



}
