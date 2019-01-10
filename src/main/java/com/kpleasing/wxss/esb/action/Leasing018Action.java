package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.Order;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.LEASING018Request;
import com.kpleasing.wxss.esb.response.LEASING018Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

@Service
public class Leasing018Action extends AbastractBaseAction<LEASING018Request, LEASING018Response, Order>  {
	
	private static final Logger logger = Logger.getLogger(Leasing018Action.class);
	
	@Override
	protected LEASING018Request generateRequestEntity(Configurate config, Order order, Map<String, String> map)
			throws WXSSException {
		LEASING018Request leasing018Request = new LEASING018Request();
		leasing018Request.setApi_code("LEASING018");
		leasing018Request.setReq_serial_no(StringUtil.getSerialNo32());
		leasing018Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		leasing018Request.setSecurity_code(config.ESB_SEC_CODE);
		leasing018Request.setSecurity_value(config.ESB_SEC_VALUE);
		leasing018Request.setName(order.getUserName());
		leasing018Request.setPhone(order.getPhone());
		leasing018Request.setCert_type("ID_CARD");
		leasing018Request.setCert_code(order.getCertId());

		try {
			leasing018Request.setSign(Security.getSign(leasing018Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		return leasing018Request;
	}

	
	@Override
	protected LEASING018Response initResponseEntity() {
		return new LEASING018Response();
	}
}
