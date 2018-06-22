package com.kpleasing.wxss.web.service;

import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;

public interface OrderService {

	/**
	 * 用户登录信息查找其下单
	 * 
	 * @param loginUser
	 * @return
	 */
	public Order getCurrentOrderByLoginUser(LoginUser loginUser);

	
	/**
	 * 
	 * @param order 
	 * @param bpId
	 * @param bpCode
	 * @param planId
	 * @param saleId 
	 */
	public void setBpInfo(Order order, String bpId, String bpCode, String planId, String saleId);


	/**
	 * 
	 * @param custId
	 * @param stepNo
	 */
	public void updateStepNo(Integer custId, byte stepNo);


	
	/**
	 * 
	 * @param order
	 * @param stepNo
	 */
	public void updateStepNo(Order order, byte stepNo);


	/**
	 * 
	 * @param userId
	 * @param bpId
	 * @param planId
	 */
	public void initFaceVideoInfo(String custId, String bpId, String planId);


	/**
	 * 
	 * @param custId
	 * @return
	 * @throws WXSSException 
	 */
	public String getRunningOrder(String custId) throws WXSSException;

}
