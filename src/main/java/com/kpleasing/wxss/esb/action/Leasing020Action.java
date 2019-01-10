package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.LEASING020Request;
import com.kpleasing.wxss.esb.response.LEASING020Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;


@Service
public class Leasing020Action extends AbastractBaseAction<LEASING020Request, LEASING020Response, BankInfo> {

	private static final Logger logger = Logger.getLogger(Leasing020Action.class);
	
	@Override
	protected LEASING020Request generateRequestEntity(Configurate config, BankInfo m, Map<String, String> map)
			throws WXSSException {
		LEASING020Request leasing020Request = new LEASING020Request();
		leasing020Request.setApi_code("LEASING020");
		leasing020Request.setReq_serial_no(StringUtil.getSerialNo32());
		leasing020Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		leasing020Request.setSecurity_code(config.ESB_SEC_CODE);
		leasing020Request.setSecurity_value(config.ESB_SEC_VALUE);
		leasing020Request.setCert_type("ID_CARD");
		leasing020Request.setCert_code(map.get("certCode"));
		leasing020Request.setSpdb_stCard_no(map.get("stCardNo"));

		try {
			leasing020Request.setSign(Security.getSign(leasing020Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		return leasing020Request;
	}

	@Override
	protected LEASING020Response initResponseEntity() {
		return new LEASING020Response();
	}

}
