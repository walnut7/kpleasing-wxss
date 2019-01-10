package com.kpleasing.wxss.web.service;

import java.util.List;

import com.kpleasing.wxss.entity.SpSales;
import com.kpleasing.wxss.enums.LOGIN_TYPE;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;

public interface LoginService {

	/**
	 * 发送短信验证码，注册验证用户身份
	 * 
	 * @param phone
	 * @param verifCode
	 */
	public void sendVerifCode(String phone, String verifCode);

	
	/**
	 * 根据手机号查询用户概要信息
	 * 
	 * @param loginUser
	 * @param manual 
	 * @return
	 * @throws WXSSException 
	 */
	public LoginUser getLoginUser(LoginUser loginUser, LOGIN_TYPE LTYPE) throws WXSSException;

	
	/**
	 * 根据微信openid获取用户信息
	 * @param loginUser 
	 * 
	 * @param openId
	 * @return
	 * @throws WXSSException 
	 */
	public LoginUser getLoginUserByOpenId(String openId) throws WXSSException;
	
	
	/**
	 * 注册用户
	 * 
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	public LoginUser generateLoginUser(LoginUser loginUser);


	/**
	 * 
	 * @param bpId
	 * @return
	 */
	public List<SpSales> getSaleListByBpId(String bpId);


	/**
	 * 
	 * @param loginUser
	 * @throws WXSSException 
	 */
	public void verifyAutoLogin(LoginUser loginUser) throws WXSSException;


	/**
	 * 
	 * @param loginUser
	 */
	public void syncRemoteCustInfo(LoginUser loginUser);

}
