package com.kpleasing.wxss.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.SpSales;
import com.kpleasing.wxss.enums.DEBUG;
import com.kpleasing.wxss.enums.LOGIN_TYPE;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.web.service.ConfigService;
import com.kpleasing.wxss.web.service.LoginService;


/**
 * 
 * @author howard.huang
 */
@Controller
@RequestMapping(value = "")
public class LoginController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ConfigService configService;
	
	@RequestMapping(value = "login")
	public ModelAndView doLogin(LoginUser reqUser, HttpServletRequest request) {
		logger.info("start login.......");
		
		String paramters = request.getQueryString()==null?"":"?"+request.getQueryString();
		logger.info("the paramters is .." + paramters);
		
		ModelMap model = new ModelMap();
		if(null!=request.getQueryString() && request.getQueryString().indexOf("&")>-1) {
			String[] ps = request.getQueryString().split("&");
			for(int i=0; i<ps.length; i++) {
				model.put(ps[i].split("=")[0], ps[i].split("=")[1]);
			}
			logger.info(model.toString());
		}
		
		try {
			if(null == reqUser || StringUtils.isBlank(reqUser.getPhone())) {
				logger.info("phone is null!");
				return new ModelAndView("login", model);
			}
			
			// 验证码校验
			HttpSession session = request.getSession(false);
			Configurate config = configService.getConfig();
			LoginUser loginUser = (LoginUser) session.getAttribute(USER_INFO);
			if(null == loginUser || null==reqUser || StringUtils.isBlank(reqUser.getVerifCode())) {
				model.put("loginInfo", "手机验证码不正确！");
				return new ModelAndView("login", model);
			}
			
			loginUser.setLoginType(LOGIN_TYPE.MANUAL.CODE);
			if(config.DEBUG_LEVEL < DEBUG.LEVEL2.CODE) {
				if(!reqUser.getVerifCode().equals(loginUser.getVerifCode()) ||
						DateUtil.compareDateDiff(DateUtil.getAfterSeconds(DateUtil.str2Date(loginUser.getStarttime(), DateUtil.yyyy_MM_ddHHmmss), config.VERIF_INTERVAL), DateUtil.getDate())<0) {
					logger.info("current date：" + DateUtil.getDate() + " session Date: " + loginUser.getStarttime());
					model.put("loginInfo", "手机验证码输入错误！");
					return new ModelAndView("login", model);
				}
			}
			
			// 用户注册
			logger.info("开始注册用户!");
			LoginUser sysUser = loginService.getLoginUser(loginUser, LOGIN_TYPE.MANUAL);
			if(null == sysUser || StringUtils.isBlank(sysUser.getUserId())) {
				sysUser = loginService.generateLoginUser(loginUser);
			}
			loginUser.setUserId(sysUser.getUserId());
			loginUser.setVerifCode(null);
			
			logger.info("set session login.......");
			session.setAttribute(USER_INFO, loginUser);
			return new ModelAndView("redirect:register/route" + paramters);
		} catch(WXSSException e) { 
			logger.error("login error :"+e.getDesc(), e);
			model.put("loginInfo", e.getDesc());
		} catch (Exception e) {
			logger.error("login error :"+e.getMessage(), e);
			model.put("loginInfo", "注册失败！");
		}
		return new ModelAndView("login", model);
	}
	
	
	@RequestMapping(value = "login2")
	public ModelAndView autoLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.info("start auto login.......");

		ModelMap model = new ModelMap();
		try {
			LoginUser loginUser = new LoginUser();
			loginUser.setUserId(request.getParameter("cust_id"));
			loginUser.setPhone(request.getParameter("phone"));
			loginUser.setCertCode(request.getParameter("cert_code"));
			loginUser.setTimeStamp(request.getParameter("time_stamp"));
			loginUser.setSign(request.getParameter("sign"));
			loginUser.setLoginType(LOGIN_TYPE.AUTO.CODE);
			
			loginService.verifyAutoLogin(loginUser);

			HttpSession session = request.getSession(false);
			LoginUser sysUser = loginService.getLoginUser(loginUser, LOGIN_TYPE.AUTO);
			loginService.syncRemoteCustInfo(loginUser);
			
			logger.info("set session login.......");
			session.setAttribute("LOGIN_USER", sysUser);
			return new ModelAndView("redirect:register/route");
		} catch(WXSSException e) {
			logger.error("login error :" + e.getDesc(), e);
			model.put("error_desc", e.getDesc());
		} catch (Exception e) {
			logger.error("login error :" + e.getMessage(), e);
			model.put("error_desc", "注册失败！");
		}
		return new ModelAndView("redirect:error", model);
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {
		logger.info("start logout.......");
		HttpSession session = request.getSession(false);
		session.setAttribute("LOGIN_USER", null);
		return "login";
	}
	
	@RequestMapping(value = "error", method = RequestMethod.GET)
	public String goError(HttpServletRequest request, HttpServletResponse response) {
		return "error";
	}
	
	
	/**
	 * 发送短信验证码
	 * @param request
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "sendVerifCode", method = RequestMethod.POST)
	public @ResponseBody String sendSMSOfVerifCode(HttpServletRequest request, String phone) {
		try{
			logger.info("send verif code to phone=" + phone + " start.......");
			
			HttpSession session = request.getSession(false);
			LoginUser loginUser = (LoginUser) session.getAttribute(USER_INFO);
			if(null == loginUser) {
				loginUser = new LoginUser();
			}
			loginUser.setPhone(phone);
			loginUser.setStarttime(DateUtil.date2Str(DateUtil.getDate(), DateUtil.yyyy_MM_ddHHmmss));
			loginUser.setVerifCode(StringUtil.getRandomVerifCode6());
			
			logger.info("verification code is :" + loginUser.getVerifCode() + ".......");
			Configurate config = configService.getConfig();
			if(config.DEBUG_LEVEL < DEBUG.LEVEL1.CODE) {
				final String __phone = loginUser.getPhone();
				final String __verif_code = loginUser.getVerifCode();
				new Thread() {
					public void run() {
						loginService.sendVerifCode(__phone, __verif_code);
					}
				}.start();
			}
			
			session.setAttribute(USER_INFO, loginUser);
			return "{\"result\":\"success\",\"message\":\"短信验证码发送成功\"}";
		} catch(Exception e) {
			logger.error("verification code error : ", e);
		}
		return "{\"result\":\"error\",\"message\":\"短信验证码发送异常\"}";
	}
	
	
	/**
	 * 
	 * @param request
	 * @param bpId
	 * @return
	 */
	@RequestMapping(value = "getSaleList", method = RequestMethod.POST)
	public @ResponseBody String getSaleList(HttpServletRequest request, String bpId) {
		try{
			logger.info("get bussiness partner's sales bpid=" + bpId + " start.......");
			
			List<SpSales> sales = loginService.getSaleListByBpId(bpId);
			StringBuilder strSales = new StringBuilder();
			strSales.append("[");
			for(SpSales sale : sales) {
				strSales.append("{\"label\":\"").append(sale.getSaleName()).append("\",")
				       .append("\"value\":\"").append(sale.getSaleId()).append("\",},");
			}
			strSales.append("]");
			
			return strSales.toString();
		} catch(Exception e) {
			logger.error(" error : ", e);
		}
		return "[]";
	}
}