package com.kpleasing.wxss.esb.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.DocumentException;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.CRM004Request;
import com.kpleasing.wxss.esb.response.CRM004AccountBean;
import com.kpleasing.wxss.esb.response.CRM004ContactBean;
import com.kpleasing.wxss.esb.response.CRM004Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.util.XMLHelper;

@Service
public class CRM004Action extends AbastractBaseAction<CRM004Request, CRM004Response, String> {
	
	private static final Logger logger = Logger.getLogger(CRM004Action.class);

	@Override
	protected CRM004Request generateRequestEntity(Configurate config, String custId, Map<String, String> map)
			throws WXSSException {
		CRM004Request crm004Request = new CRM004Request();
		crm004Request.setApi_code("LEASING009");
		crm004Request.setReq_serial_no(StringUtil.getSerialNo32());
		crm004Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		crm004Request.setSecurity_code(config.ESB_SEC_CODE);
		crm004Request.setSecurity_value(config.ESB_SEC_VALUE);
		crm004Request.setCust_id(custId);
		
		try {
			crm004Request.setSign(Security.getSign(crm004Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		
		return crm004Request;
	}
	
	
	/**
	 * 
	 * @param respXml
	 * @return
	 * @throws WXSSException 
	 */
	@Override
	protected void parseResponseEntity(String resultXml, CRM004Response crm004Response) throws WXSSException {
		logger.info("转换XML报文为实体Bean......");
		try {
			logger.info("客户信息......");
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(resultXml, "/crm/head|/crm/body", false);
			for (Map<String, String> map : mapList) {
				BeanUtils.populate(crm004Response, map);
			}

			logger.info("转换联系人信息......");
			List<Map<String, String>> mapList1 = XMLHelper.parseMultNodesXml(resultXml, "//contact");
			List<CRM004ContactBean> contacts = new ArrayList<CRM004ContactBean>();
			for (Map<String, String> map1 : mapList1) {
				CRM004ContactBean contact = new CRM004ContactBean();
				BeanUtils.populate(contact, map1);
				contacts.add(contact);
			}
			crm004Response.setContacts(contacts);

			logger.info("转换账户信息......");
			List<Map<String, String>> mapList2 = XMLHelper.parseMultNodesXml(resultXml, "//account");
			List<CRM004AccountBean> accounts = new ArrayList<CRM004AccountBean>();
			for (Map<String, String> map2 : mapList2) {
				CRM004AccountBean account = new CRM004AccountBean();
				BeanUtils.populate(account, map2);
				accounts.add(account);
			}
			crm004Response.setAccounts(accounts);

		} catch (IllegalAccessException | InvocationTargetException | DocumentException e) {
			logger.error("请求报文XML解析处理错误." + e.getMessage(), e);
			throw new WXSSException("FAILED", "XML解析失败！");
		}
	}

	@Override
	protected CRM004Response initResponseEntity() {
		return new CRM004Response();
	}
}
