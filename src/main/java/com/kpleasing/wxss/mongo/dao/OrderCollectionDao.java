package com.kpleasing.wxss.mongo.dao;

import java.util.List;

import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.mongo.collections.OrderCollection;

public interface OrderCollectionDao {
	
	/**
	 * 
	 * @param user
	 */
    public void insertOrderCollection(Order order, String operType);

    
    /**
     * 
     * @return
     */
	public List<OrderCollection> findAll();
}
