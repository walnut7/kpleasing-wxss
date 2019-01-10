package com.kpleasing.wxss.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.web.service.ConfigService;
import com.kpleasing.wxss.web.service.LoginService;
import com.kpleasing.wxss.web.service.OrderService;


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
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "login")
	public ModelAndView doLogin(LoginUser reqUser, HttpServletRequest request) {
		logger.info("start login.......");
		// String paramters = request.getQueryString()==null?"":"?"+request.getQueryString();
		// logger.info("the paramters is .." + paramters);
		
		ModelMap model = new ModelMap();
		model.put("param", reqUser);
		
		StringBuilder paramters =  new StringBuilder();
		paramters.append("?bpId=").append(reqUser.getBpId()).append("&bpCode=").append(reqUser.getBpCode())
		.append("&modelId=").append(reqUser.getModelId()).append("&planId=").append(reqUser.getPlanId())
		.append("&saleId=").append(reqUser.getSaleId());
		
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
				logger.info("手机验证码不正确！");
				model.put("loginInfo", "手机验证码不正确！");
				return new ModelAndView("login", model);
			}
			
			loginUser.setLoginType(LOGIN_TYPE.MANUAL.CODE);
			if(config.DEBUG_LEVEL < DEBUG.LEVEL2.CODE) {
				if(!reqUser.getVerifCode().equals(loginUser.getVerifCode())) {
						// || DateUtil.compareDateDiff(DateUtil.getAfterSeconds(DateUtil.str2Date(loginUser.getStarttime(), DateUtil.yyyy_MM_ddHHmmss), config.VERIF_INTERVAL), DateUtil.getDate())<0) {
					logger.info("手机验证码输入错误！\t输入的验证码：" + reqUser.getVerifCode() + "\t发送的验证码：" + loginUser.getVerifCode());
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
			return new ModelAndView("redirect:register/route" + paramters.toString());
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
	
	
	
	/**
	 * 
	 * @param reqUser
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "authentication")
	public ModelAndView AuthLogin(LoginUser reqUser, HttpServletRequest request) {
		logger.info("start authorization login.......");
		ModelMap model = new ModelMap();
		Configurate config = configService.getConfig();
		try {			
			HttpSession session = request.getSession(false);
			LoginUser loginUser = (LoginUser) session.getAttribute(USER_INFO);	
			if(null==loginUser || StringUtils.isBlank(loginUser.getUserId())) {
				
				if(StringUtils.isNotBlank(loginUser.getOpenId())) {
					logger.info("get login user by session openid.......");
					LoginUser sysLoginUser = loginService.getLoginUserByOpenId(loginUser.getOpenId());
					
					if(null != sysLoginUser) {
						loginUser.setUserId(sysLoginUser.getUserId());
						loginUser.setPhone(sysLoginUser.getPhone());
						loginUser.setUserName(sysLoginUser.getUserName());
						loginUser.setLoginType(sysLoginUser.getLoginType());
						loginUser.setCertCode(sysLoginUser.getCertCode());
						
						logger.info("set session login.......");
						session.setAttribute(USER_INFO, loginUser);
						return new ModelAndView("redirect:authentication");
					}
				}
				
				if(null == reqUser || StringUtils.isBlank(reqUser.getPhone())) {
					logger.info("手机号phone为空!");
					//  model.put("loginInfo", "手机号不能为空！");
					return new ModelAndView("login3", model);
				}
					
				// 验证码校验
				if(null == loginUser || null==reqUser || StringUtils.isBlank(reqUser.getVerifCode())) {
					logger.info("手机验证码不正确！");
					model.put("loginInfo", "手机验证码不正确！");
					return new ModelAndView("login3", model);
				}
					
				loginUser.setLoginType(LOGIN_TYPE.MANUAL.CODE);
				if(config.DEBUG_LEVEL < DEBUG.LEVEL2.CODE) {
					if(!reqUser.getVerifCode().equals(loginUser.getVerifCode())) {
							// || DateUtil.compareDateDiff(DateUtil.getAfterSeconds(DateUtil.str2Date(loginUser.getStarttime(), DateUtil.yyyy_MM_ddHHmmss), config.VERIF_INTERVAL), DateUtil.getDate())<0) {
						logger.info("手机验证码输入错误！\t输入的验证码：" + reqUser.getVerifCode() + "\t发送的验证码：" + loginUser.getVerifCode());
						model.put("loginInfo", "手机验证码输入错误！");
						return new ModelAndView("login3", model);
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
				return new ModelAndView("redirect:authentication");
			
			} else {
				String custId = loginUser.getUserId();
				logger.info("根据客户id【"+custId+"】查找订单");
				String applyNo = orderService.getRunningOrder(custId, true);
				
				if(StringUtils.isNotBlank(applyNo)) {
					String content = applyNo + config.LEASING_CONTACT_KEY;
					logger.info("md5 content......." + content);
					String sign = Security.MD5Encode(content);
					String redirectPath = config.LEASING_CONTACT_URL+"?applyno="+applyNo+"&sign="+sign.toUpperCase();
					
					logger.info("redirectPath......." + redirectPath);
					return new ModelAndView("redirect:"+redirectPath, model);
				} else {
					return new ModelAndView("warntip", model); 
				}
			}
		} catch (WXSSException e) {
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("login3", model);
	}
	
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {
		logger.info("start logout.......");
		try {
			HttpSession session = request.getSession(false);
			session.setAttribute(USER_INFO, null);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "redirect:login";
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
			
			// 30分钟后接触短信发送上限限制
			if(StringUtils.isNotBlank(loginUser.getStarttime())) {
				logger.info("超时时间：" + DateUtil.getAfterSeconds(DateUtil.str2Date(loginUser.getStarttime(), DateUtil.yyyy_MM_ddHHmmss), 1800));
				logger.info("当前时间：" + DateUtil.getDate());
				if(DateUtil.compareDateDiff(DateUtil.getAfterSeconds(DateUtil.str2Date(loginUser.getStarttime(), DateUtil.yyyy_MM_ddHHmmss), 1800), DateUtil.getDate())<0) {
					logger.info("超过30分钟，解除发送上限限制.");
					loginUser.setTimes(0);
				}
			}
			
			int t = loginUser.getTimes() + 1;
			logger.info("第" + t + "次发送验证码.");
			if(t>5) {
				return "{\"result\":\"failed\",\"message\":\"超过发送上限，请稍后重试。若您一直未收到短信验证码，请与服务人员联系!\"}";
			}
			loginUser.setTimes(t);
			loginUser.setStarttime(DateUtil.date2Str(DateUtil.getDate(), DateUtil.yyyy_MM_ddHHmmss));
			if(StringUtils.isBlank(loginUser.getVerifCode())) {
				loginUser.setVerifCode(StringUtil.getRandomVerifCode6());
			}
			
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
			if(StringUtils.isBlank(bpId)) {
				logger.error("bpid is null!");
				return "[]";
			}
			
			List<SpSales> sales = loginService.getSaleListByBpId(bpId);
			
			Map<String, List<Map<String, String>>> map = new TreeMap<String, List<Map<String, String>>>();
			for(SpSales s : sales) {
				String pinying = StringUtil.toPingyin(s.getSaleName());
				String py = pinying.substring(0,1);
				
				if(null == map.get(py) || map.get(py).isEmpty()) {
					List<Map<String, String>> list = new ArrayList<Map<String, String>>();
					Map<String, String> m = new HashMap<String, String>();
					m.put("name", s.getSaleName()+"("+s.getSalePhone().substring(0, 3)+"****"+s.getSalePhone().substring(7)+")");
					m.put("tags", pinying+","+s.getSaleName());
					m.put("spid", String.valueOf(s.getSaleId()));
					list.add(m);
					
					map.put(py, list);
				} else {
					List<Map<String, String>> list = map.get(py);
					
					Map<String, String> m = new HashMap<String, String>();
					m.put("name", s.getSaleName()+"("+s.getSalePhone().substring(0, 3)+"****"+s.getSalePhone().substring(7)+")");
					m.put("tags", pinying+","+s.getSaleName());
					m.put("spid", String.valueOf(s.getSaleId()));
					list.add(m);
				}
			}
			
			StringBuilder strSales = new StringBuilder();
			strSales.append("[");
			for (Map.Entry<String, List<Map<String, String>>> entry : map.entrySet()) {
				
				List<Map<String, String>> listM = entry.getValue();
				
				strSales.append("{\"name\":\"").append(entry.getKey()).append("\",").append("\"people\":[");
				for(Map<String, String> p : listM) {
					strSales.append("{\"name\":\"").append(p.get("name")).append("\",")
				       .append("\"tags\":\"").append(p.get("tags")).append("\",")
				       .append("\"spid\":\"").append(p.get("spid")).append("\"},");
				}
				strSales.append("]},");
			}
			strSales.append("]");
			
//			for(SpSales sale : sales) {
//				strSales.append("{\"label\":\"").append(sale.getSaleName()).append("\",")
//				       .append("\"value\":\"").append(sale.getSaleId()).append("\",},");
//			}
			return strSales.toString();
		} catch(Exception e) {
			logger.error(" error : "+e.getMessage(), e);
		}
		return "[]";
	}
}