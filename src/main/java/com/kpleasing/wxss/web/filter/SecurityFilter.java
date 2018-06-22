package com.kpleasing.wxss.web.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import com.kpleasing.wxss.esb.request.CRM002Request;
import com.kpleasing.wxss.esb.response.CRM002Response;
import com.kpleasing.wxss.exception.InputParamException;
import com.kpleasing.wxss.pojo.LoginUser;
import com.kpleasing.wxss.util.ConfigUtil;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.EncryptUtil;
import com.kpleasing.wxss.util.HttpHelper;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.util.XMLHelper;

/**
 * 
 * @author howard.huang 
 * @date 2018-02-28
 *
 */
public class SecurityFilter implements Filter {
	public FilterConfig config;
	private static Logger logger = Logger.getLogger(SecurityFilter.class);

	@Override
	public void destroy() {
		this.config = null;
	}
	
	private boolean isIgnore(String uri, String[] argc) {
		try{
			
			if(uri.indexOf("/")!=-1) {
				String module = uri.split("/")[2].toLowerCase();
				for(int i=0; i<argc.length; i++) {
					if(module.equals(argc[i])) {
						return false;
					}
				}
			}
		} catch(Exception e) {}
		return true;
	}
	
	
	private boolean isLoginPage(HttpServletRequest request) {
		try{
			return request.getRequestURI().split("/")[2].toLowerCase().indexOf("login")!=-1?true:false;
		} catch(Exception e) {}
		return false;
	}
	
	
	private boolean isWechatBrower(HttpServletRequest request) {
		return request.getHeader("User-Agent").toLowerCase().indexOf("micromessenger")!=-1?true:false;
	}
	
	
	private LoginUser getUserByWxOpenId(String openid) {
		try {
			LoginUser loginUser = new LoginUser();
			loginUser.setOpenId(openid);
			
			ConfigUtil conf = ConfigUtil.getInstance();
			CRM002Request crm002Request = new CRM002Request();
			crm002Request.setApi_code("CRM002");
			crm002Request.setReq_serial_no(StringUtil.getSerialNo32());
			crm002Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
			crm002Request.setSecurity_code(conf.getPropertyParam("esb.security_code"));
			crm002Request.setSecurity_value(conf.getPropertyParam("esb.security_value"));
			crm002Request.setChannel_type("1");
			crm002Request.setChannel_id(openid);
			crm002Request.setSign(Security.getSign(crm002Request, conf.getPropertyParam("esb.sign_key")));
			
			String requestMsg = EncryptUtil.encrypt(conf.getPropertyParam("esb.des_key"), conf.getPropertyParam("esb.des_iv"), generateRequestXML(crm002Request));
			
			logger.info("开始发起请求......");
			String responseMsg = HttpHelper.doHttpPost(conf.getPropertyParam("esb.server_url"), requestMsg);
			String ResponseXml = EncryptUtil.decrypt(conf.getPropertyParam("esb.des_key"), conf.getPropertyParam("esb.des_iv"), responseMsg);
			logger.info("CRM002返回明文信息：" + ResponseXml);
			
			CRM002Response crm002Response = new CRM002Response();
			XMLHelper.getBeanFromXML(ResponseXml, crm002Response);
			
			// 验证数据防篡改
			verification(crm002Response, conf);
			
			if("SUCCESS".equals(crm002Response.getReturn_code()) && "SUCCESS".equals(crm002Response.getResult_code())) {
				loginUser.setUserId(crm002Response.getCust_id());
				loginUser.setPhone(crm002Response.getPhone());
				// loginUser.setLoginType(LOGIN_TYPE.MANUAL.CODE);
			}
			return loginUser;
		} catch(Exception e) {
			logger.error("错误信息："+e.getMessage(), e);
		}
		return null;
	}

	
	private void verification(CRM002Response crm002Response, ConfigUtil conf) throws InputParamException {
		// 签名校验
		String signFromAPIResponse = crm002Response.getSign();
		if (StringUtils.isBlank(signFromAPIResponse)) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", crm002Response.getReq_serial_no(), crm002Response.getReq_date());
		}
		
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		crm002Response.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(crm002Response, conf.getPropertyParam("esb.sign_key"));
			
			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", crm002Response.getReq_serial_no(), crm002Response.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", crm002Response.getReq_serial_no(), crm002Response.getReq_date());
		}
	}
	

	private String generateRequestXML(CRM002Request crm002Request) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder reqXML = new StringBuilder();
		reqXML.append("<?xml version=\"1.0\"?>")
		.append("<esb><head>")
		.append("<api_code>").append(crm002Request.getApi_code()).append("</api_code>")
		.append("<req_serial_no>").append(crm002Request.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(crm002Request.getReq_date()).append("</req_date>")
		.append("<security_code>").append(crm002Request.getSecurity_code()).append("</security_code>")
		.append("<security_value>").append(crm002Request.getSecurity_value()).append("</security_value>")
		.append("<sign>").append(crm002Request.getSign()).append("</sign>")
		.append("</head><body>");
		reqXML.append(XMLHelper.getXMLFromBean(crm002Request));
		reqXML.append("</body></esb>");
		
		logger.info("ESB接口CRM002请求报文明文：" + reqXML.toString());
		return reqXML.toString();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponseWrapper wrapperResponse = new HttpServletResponseWrapper((HttpServletResponse) response);
			
			String paramters = httpRequest.getQueryString()==null?"":"?"+httpRequest.getQueryString();
	        String intercepts = config.getInitParameter("interceptRs");
	        String redirectPath = httpRequest.getContextPath() + config.getInitParameter("redirectPath") + paramters;
	        String defaultPath = httpRequest.getContextPath() + config.getInitParameter("defaultPath") + paramters;
	        
	        if(!isIgnore(httpRequest.getRequestURI(), new String[]{"login2"})) {
	        	logger.info("====忽略-自动登录跳转！=======");
				chain.doFilter(request, response);
				return;
			}
	        
	        HttpSession session = httpRequest.getSession();
			LoginUser user = (LoginUser) session.getAttribute("LOGIN_USER");
			String openid = httpRequest.getParameter("openid");
			if((null==user || StringUtils.isBlank(user.getOpenId())) && isWechatBrower(httpRequest)) {
		        if(StringUtils.isBlank(openid)) {
		        	ConfigUtil conf = ConfigUtil.getInstance();
		    		String redirectUrl = conf.getPropertyParam("wxauth.redirect_url") + httpRequest.getRequestURI() + paramters;
		        	String result = "redirect_url=" + redirectUrl + "&key=" + conf.getPropertyParam("wxauth.key");
		        	logger.info("Sign Before MD5:" + result);
		    		String sign = Security.MD5Encode(result).toUpperCase();
		    		logger.info("Sign Result:" + sign);
		
		        	String url = conf.getPropertyParam("wxauth.server_url") + "?redirect_url="+URLEncoder.encode(redirectUrl, "utf-8")+"&sign="+sign;
		    		logger.info("wechat auth fullpath：" + url);
		        	wrapperResponse.sendRedirect(url);
		        	return;
		        } else {
	        		session.setAttribute("LOGIN_USER", getUserByWxOpenId(openid));
				}
			}
			
			if(isIgnore(httpRequest.getRequestURI(), intercepts.split(";"))) {
				chain.doFilter(request, response);
				return;
			}
	        
	        
			if(null == user || StringUtils.isBlank(user.getUserId()) || StringUtils.isBlank(user.getPhone())) {
				if(!isLoginPage(httpRequest)) {
					wrapperResponse.sendRedirect(redirectPath);
					return;
				} 
			} else {
				if(isLoginPage(httpRequest)) {
					logger.info("login default path is：" + defaultPath);
					wrapperResponse.sendRedirect(defaultPath);
					return;
				}
			}
		} catch(Exception e) {
			logger.error("错误信息："+e.getMessage(), e);
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}
}