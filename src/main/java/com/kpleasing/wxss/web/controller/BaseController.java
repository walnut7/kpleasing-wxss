package com.kpleasing.wxss.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;
import com.kpleasing.wxss.web.service.OrderService;

public abstract class BaseController {
	
	public static final String USER_INFO = "LOGIN_USER";
	
	@Autowired
	private OrderService orderService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		dateFormat.setLenient(false);  
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	public String getLoginUserCustId(HttpServletRequest request) throws WXSSException {
		HttpSession session = request.getSession(false);
		LoginUser loginUser = (LoginUser) session.getAttribute(USER_INFO);
		if(null==loginUser || StringUtils.isBlank(loginUser.getUserId())) {
			throw new WXSSException("ERROR", "the cust id is null!");
		} else {
			Order order = orderService.getCurrentOrderByLoginUser(loginUser);
			loginUser.setLoginType(order.getLoginChannel());
			loginUser.setUserName(order.getUserName());
			loginUser.setCertCode(order.getCertId());
			session.setAttribute(USER_INFO, loginUser);
		}
		return loginUser.getUserId();
	}
	
	
	public LoginUser getLoginUserObject(HttpServletRequest request) throws WXSSException {
		HttpSession session = request.getSession(false);
		LoginUser loginUser = (LoginUser) session.getAttribute(USER_INFO);
		if(null==loginUser || StringUtils.isBlank(loginUser.getUserId())) {
			throw new WXSSException("ERROR", "the cust id is null!");
		} else {
			Order order = orderService.getCurrentOrderByLoginUser(loginUser);
			loginUser.setLoginType(order.getLoginChannel());
			loginUser.setUserName(order.getUserName());
			loginUser.setCertCode(order.getCertId());
			session.setAttribute(USER_INFO, loginUser);
		}
		return loginUser;
	}
}
