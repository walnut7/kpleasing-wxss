package com.kpleasing.wxss.esb;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.DocumentException;
import org.jboss.logging.Logger;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.EncryptUtil;
import com.kpleasing.wxss.util.HttpHelper;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.XMLHelper;
import com.kpleasing.wxss.esb.request.ESBRequestHead;
import com.kpleasing.wxss.esb.response.ESBResponseHead;

public abstract class AbastractBaseAction<T, E, M> {
	
	private static final Logger logger = Logger.getLogger(AbastractBaseAction.class);
	
	/**
	 * 
	 * @param t
	 * @param config
	 * @return
	 * @throws WXSSException 
	 */
	protected abstract T generateRequestEntity(Configurate config, M m, Map<String, String> map) throws WXSSException ;
	

	/**
	 * 
	 * @param m
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected String generateRequestXML(T t) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder requestXml = new StringBuilder();
		requestXml.append("<?xml version=\"1.0\"?>")
		.append("<esb><head>")
		.append("<api_code><![CDATA[").append(((ESBRequestHead) t).getApi_code()).append("]]></api_code>")
		.append("<req_serial_no><![CDATA[").append(((ESBRequestHead) t).getReq_serial_no()).append("]]></req_serial_no>")
		.append("<req_date><![CDATA[").append(((ESBRequestHead) t).getReq_date()).append("]]></req_date>")
		.append("<security_code><![CDATA[").append(((ESBRequestHead) t).getSecurity_code()).append("]]></security_code>")
		.append("<security_value><![CDATA[").append(((ESBRequestHead) t).getSecurity_value()).append("]]></security_value>")
		.append("<sign><![CDATA[").append(((ESBRequestHead) t).getSign()).append("]]></sign>")
		.append("</head><body>");
		
		requestXml.append(XMLHelper.getXMLFromBean(t));
		requestXml.append("</body></esb>");
		
		return requestXml.toString();
	}
	
	
	/**
	 * 实例化响应实体
	 * @return
	 */
	protected abstract E initResponseEntity() ;
	
	
	/**
	 * 
	 * @param respXml
	 * @return
	 * @throws WXSSException 
	 */
	protected void parseResponseEntity(String respXml, E e) throws WXSSException {
		logger.info("转换XML报文为实体Bean......");
		try {
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(respXml, "/esb/head|/esb/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(e, map);
			}
		} catch (IllegalAccessException | InvocationTargetException | DocumentException ex) {
			logger.error("请求报文XML解析处理错误."+ex.getMessage(), ex);
			throw new WXSSException("FAILED", "XML解析失败！");
		}
	}
	
	
	/**
	 * 签名校验
	 * @param n
	 * @param config
	 * @throws WXSSException 
	 */
	protected void verifyResponse(E e, Configurate config) throws WXSSException {
		if("SUCCESS".equals(((ESBResponseHead) e).getReturn_code())) {
			String signFromAPIResponse = ((ESBResponseHead) e).getSign();
			if (signFromAPIResponse == "" || signFromAPIResponse == null) {
				throw new WXSSException("FAILED", "CRM API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			}
			
			logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
			((ESBResponseHead) e).setSign("");
			try {
				String signForAPIResponse = Security.getSign(e, config.ESB_SIGN_KEY);
				
				if (!signForAPIResponse.equals(signFromAPIResponse)) {
					throw new WXSSException("FAILED", "CRM API返回的数据签名验证不通过，有可能被第三方篡改!!!");
				}
			} catch (IllegalAccessException ex) {
				logger.error(ex.getMessage(), ex);
				throw new WXSSException("FAILED", "CRM API签名出錯!!!");
			}
		} else {
			throw new WXSSException("FAILED", ((ESBResponseHead) e).getReturn_desc());
		}
	}
	
	
	/**
	 * 
	 * @param t
	 * @param config
	 * @return
	 * @throws WXSSException 
	 */
	public E doRequest(Configurate config, M m, Map<String, String> map) throws WXSSException {
		try {
			T t = generateRequestEntity(config, m, map);
			
			String requestXml = generateRequestXML(t);
			logger.info("请求明文：" + requestXml);
			
			String requestMsg = EncryptUtil.encrypt(config.ESB_DES_KEY, config.ESB_DES_IV, requestXml);
			logger.info("请求密文：" + requestMsg);
			
			String responseMsg = HttpHelper.doHttpPost(config.ESB_SERVER_URL, requestMsg);
			
			String responseXml = EncryptUtil.decrypt(config.ESB_DES_KEY, config.ESB_DES_IV, responseMsg);
			logger.info("响应明文：" + responseXml);
			
			E e = initResponseEntity();
			parseResponseEntity(responseXml, e);
			
			// 数据校验
			verifyResponse(e, config);
			
			return e;
			
		} catch(WXSSException ex) {
			logger.error(ex.getDesc(), ex);
			throw new WXSSException("FAILED", ex.getDesc());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new WXSSException("FAILED", ex.getMessage());
		}
	}
}
