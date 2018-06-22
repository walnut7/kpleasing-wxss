package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.XMC001Request;
import com.kpleasing.wxss.esb.response.XMC001Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;


@Service
public class XMC001Action extends AbastractBaseAction<XMC001Request, XMC001Response, String> {

	private static final Logger logger = Logger.getLogger(XMC001Action.class);
	
	@Override
	protected XMC001Request generateRequestEntity(Configurate config, String custId, Map<String, String> map)
			throws WXSSException {
		XMC001Request xmc001Request = new XMC001Request();
		xmc001Request.setApi_code("XMC001");
		xmc001Request.setReq_serial_no(StringUtil.getSerialNo32());
		xmc001Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
		xmc001Request.setSecurity_code(config.ESB_SEC_CODE);
		xmc001Request.setSecurity_value(config.ESB_SEC_VALUE);
		xmc001Request.setCust_id(custId);
		
		try {
			xmc001Request.setSign(Security.getSign(xmc001Request, config.ESB_SIGN_KEY));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new WXSSException("SIGN_ERROR", "签名错误！");
		}
		return xmc001Request;
	}
	

	@Override
	protected XMC001Response initResponseEntity() {
		return new XMC001Response();
	}

}
