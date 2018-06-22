package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.LEASING009Request;
import com.kpleasing.wxss.esb.response.LEASING009Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

@Service
public class Leasing009Action extends AbastractBaseAction<LEASING009Request, LEASING009Response, String> {

	private static final Logger logger = Logger.getLogger(Leasing009Action.class);
	
	@Override
	protected LEASING009Request generateRequestEntity(Configurate config, String custId, Map<String, String> map)
			throws WXSSException {
		LEASING009Request leasing009Request = new LEASING009Request();
		leasing009Request.setApi_code("LEASING009");
		leasing009Request.setReq_serial_no(StringUtil.getSerialNo32());
		leasing009Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		leasing009Request.setSecurity_code(config.ESB_SEC_CODE);
		leasing009Request.setSecurity_value(config.ESB_SEC_VALUE);
		leasing009Request.setCust_id(custId);
		
		try {
			leasing009Request.setSign(Security.getSign(leasing009Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		
		return leasing009Request;
	}
	

	@Override
	protected LEASING009Response initResponseEntity() {
		return new LEASING009Response();
	}
}
