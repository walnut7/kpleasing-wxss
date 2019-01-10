package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.LEASING019Request;
import com.kpleasing.wxss.esb.response.LEASING019Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

@Service
public class Leasing019Action extends AbastractBaseAction<LEASING019Request, LEASING019Response, String> {

	private static final Logger logger = Logger.getLogger(Leasing019Action.class);
	
	@Override
	protected LEASING019Request generateRequestEntity(Configurate config, String certCode, Map<String, String> map)
			throws WXSSException {
		LEASING019Request leasing019Request = new LEASING019Request();
		leasing019Request.setApi_code("LEASING019");
		leasing019Request.setReq_serial_no(StringUtil.getSerialNo32());
		leasing019Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		leasing019Request.setSecurity_code(config.ESB_SEC_CODE);
		leasing019Request.setSecurity_value(config.ESB_SEC_VALUE);
		leasing019Request.setCert_type("ID_CARD");
		leasing019Request.setCert_code(certCode);

		try {
			leasing019Request.setSign(Security.getSign(leasing019Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		return leasing019Request;
	}

	@Override
	protected LEASING019Response initResponseEntity() {
		return new LEASING019Response();
	}
}
