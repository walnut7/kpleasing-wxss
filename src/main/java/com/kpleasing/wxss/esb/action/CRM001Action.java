package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.CRM001Request;
import com.kpleasing.wxss.esb.response.CRM001Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.pojo.LoginUser;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

@Service
public class CRM001Action extends AbastractBaseAction<CRM001Request, CRM001Response, LoginUser> {

	private static final Logger logger = Logger.getLogger(CRM001Action.class);

	@Override
	protected CRM001Request generateRequestEntity(Configurate config, LoginUser loginUser, Map<String, String> map)
			throws WXSSException {
		CRM001Request crm001Request = new CRM001Request();
		crm001Request.setApi_code("CRM001");
		crm001Request.setReq_serial_no(StringUtil.getSerialNo32());
		crm001Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		crm001Request.setSecurity_code(config.ESB_SEC_CODE);
		crm001Request.setSecurity_value(config.ESB_SEC_VALUE);
		crm001Request.setPhone(loginUser.getPhone());
		if(StringUtils.isBlank(loginUser.getOpenId())) {
			crm001Request.setChannel_type("0");
		} else {
			crm001Request.setChannel_type("1");
			crm001Request.setChannel_id(loginUser.getOpenId());
		}
		
		try {
			crm001Request.setSign(Security.getSign(crm001Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		return crm001Request;
	}
	
	
	@Override
	protected CRM001Response initResponseEntity() {
		return new CRM001Response();
	}
}
