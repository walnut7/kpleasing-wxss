package com.kpleasing.wxss.esb.action;

import java.text.MessageFormat;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.LEASING001Request;
import com.kpleasing.wxss.esb.response.LEASING001Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

/**
 * 
 * @author howard.huang
 *
 */
@Service
public class Leasing001Action extends AbastractBaseAction<LEASING001Request, LEASING001Response, String> {

	private static final Logger logger = Logger.getLogger(Leasing001Action.class);
	
	@Override
	protected LEASING001Request generateRequestEntity(Configurate config, String phone, Map<String, String> map)
			throws WXSSException {
		LEASING001Request leasing001Request = new LEASING001Request();
		leasing001Request.setApi_code("LEASING001");
		leasing001Request.setReq_serial_no(StringUtil.getSerialNo32());
		leasing001Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		leasing001Request.setSecurity_code(config.ESB_SEC_CODE);
		leasing001Request.setSecurity_value(config.ESB_SEC_VALUE);
		leasing001Request.setPhone(phone);
		leasing001Request.setContent(MessageFormat.format(config.MSG_MODEL, map.get("verifCode")));
		
		try {
			leasing001Request.setSign(Security.getSign(leasing001Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		
		return leasing001Request;
	}

	
	@Override
	protected LEASING001Response initResponseEntity() {
		return new LEASING001Response();
	}
}
