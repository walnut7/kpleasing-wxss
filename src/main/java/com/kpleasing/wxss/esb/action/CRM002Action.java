package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.CRM002Request;
import com.kpleasing.wxss.esb.response.CRM002Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

@Service
public class CRM002Action extends AbastractBaseAction<CRM002Request, CRM002Response, String> {
	
	private static final Logger logger = Logger.getLogger(CRM002Action.class);

	@Override
	protected CRM002Request generateRequestEntity(Configurate config, String loginUser, Map<String, String> map)
			throws WXSSException {
		CRM002Request crm002Request = new CRM002Request();
		crm002Request.setApi_code("CRM002");
		crm002Request.setReq_serial_no(StringUtil.getSerialNo32());
		crm002Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		crm002Request.setSecurity_code(config.ESB_SEC_CODE);
		crm002Request.setSecurity_value(config.ESB_SEC_VALUE);
		crm002Request.setChannel_type(map.get("ChannelType"));
		crm002Request.setChannel_id(map.get("ChannelCode"));
		
		try {
			crm002Request.setSign(Security.getSign(crm002Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		return crm002Request;
	}

	
	@Override
	protected CRM002Response initResponseEntity() {
		return new CRM002Response();
	}
}
