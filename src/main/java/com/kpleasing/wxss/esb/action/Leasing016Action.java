package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.BankInfo;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.LEASING016Request;
import com.kpleasing.wxss.esb.response.LEASING016Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

@Service
public class Leasing016Action extends AbastractBaseAction<LEASING016Request, LEASING016Response, BankInfo> {
	
	private static final Logger logger = Logger.getLogger(Leasing016Action.class);
	
	@Override
	protected LEASING016Request generateRequestEntity(Configurate config, BankInfo bankInfo, Map<String, String> map) throws WXSSException {
		LEASING016Request leasing016Request = new LEASING016Request();
		leasing016Request.setApi_code("LEASING016");
		leasing016Request.setReq_serial_no(StringUtil.getSerialNo32());
		leasing016Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		leasing016Request.setSecurity_code(config.ESB_SEC_CODE);
		leasing016Request.setSecurity_value(config.ESB_SEC_VALUE);
		leasing016Request.setExternal_no(map.get("externalNo"));
		leasing016Request.setCust_id(map.get("custId"));
		leasing016Request.setCust_name(map.get("custName"));
		leasing016Request.setRepay_card_no(bankInfo.getRepayCardNo());
		leasing016Request.setCert_type("0");
		leasing016Request.setCert_code(map.get("certCode"));
		leasing016Request.setBank_phone(bankInfo.getBankPhone());
		
		try {
			leasing016Request.setSign(Security.getSign(leasing016Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		return leasing016Request;
	}


	@Override
	protected LEASING016Response initResponseEntity() {
		return new LEASING016Response();
	}
}
