package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.LEASING007Request;
import com.kpleasing.wxss.esb.response.LEASING007Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

@Service
public class Leasing007Action extends AbastractBaseAction<LEASING007Request, LEASING007Response, Order> {

	private static final Logger logger = Logger.getLogger(Leasing007Action.class);
	
	@Override
	protected LEASING007Request generateRequestEntity(Configurate config, Order order, Map<String, String> map) throws WXSSException {
		LEASING007Request leasing007Request = new LEASING007Request();
		leasing007Request.setApi_code("LEASING007");
		leasing007Request.setReq_serial_no(StringUtil.getSerialNo32());
		leasing007Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		leasing007Request.setSecurity_code(config.ESB_SEC_CODE);
		leasing007Request.setSecurity_value(config.ESB_SEC_VALUE);
		leasing007Request.setCust_id(order.getCustId().toString());
		leasing007Request.setCert_code(order.getCertId());
		leasing007Request.setAgent_code(order.getBpCode());
		if(null != order.getPlanId()) {
			leasing007Request.setFinance_plan(order.getPlanId().toString());
		}
		
		if(null != order.getSaleId()) {
			leasing007Request.setSale_id(order.getSaleId().toString());
		}
		
		try {
			leasing007Request.setSign(Security.getSign(leasing007Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		
		return leasing007Request;
	}

	
	@Override
	protected LEASING007Response initResponseEntity() {
		return new LEASING007Response();
	}

}
