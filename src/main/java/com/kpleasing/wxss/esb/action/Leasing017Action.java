package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.LEASING017Request;
import com.kpleasing.wxss.esb.response.LEASING017Response;
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
public class Leasing017Action extends AbastractBaseAction<LEASING017Request, LEASING017Response, BankInfo>  {
	
	private static final Logger logger = Logger.getLogger(Leasing017Action.class);

	@Override
	protected LEASING017Request generateRequestEntity(Configurate config, BankInfo bankInfo, Map<String, String> map) throws WXSSException {
		LEASING017Request leasing017Request = new LEASING017Request();
		leasing017Request.setApi_code("LEASING017");
		leasing017Request.setReq_serial_no(StringUtil.getSerialNo32());
		leasing017Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		leasing017Request.setSecurity_code(config.ESB_SEC_CODE);
		leasing017Request.setSecurity_value(config.ESB_SEC_VALUE);
		leasing017Request.setExternal_no(bankInfo.getExternalNo());
		leasing017Request.setCust_id(String.valueOf(bankInfo.getUserId()));
		leasing017Request.setRepay_card_no(bankInfo.getRepayCardNo());
		leasing017Request.setValid_code(map.get("vailidCode"));
		leasing017Request.setToken(bankInfo.getToken());
		leasing017Request.setBank_phone(bankInfo.getBankPhone());
		
		try {
			leasing017Request.setSign(Security.getSign(leasing017Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		return leasing017Request;
	}

	@Override
	protected LEASING017Response initResponseEntity() {
		return new LEASING017Response();
	}

}
