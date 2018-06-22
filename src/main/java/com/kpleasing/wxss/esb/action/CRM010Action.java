package com.kpleasing.wxss.esb.action;

import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.FaceVideo;
import com.kpleasing.wxss.esb.AbastractBaseAction;
import com.kpleasing.wxss.esb.request.CRM010Request;
import com.kpleasing.wxss.esb.response.CRM010Response;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;

@Service
public class CRM010Action extends AbastractBaseAction<CRM010Request, CRM010Response, FaceVideo> {
	
	private static final Logger logger = Logger.getLogger(CRM010Action.class);

	@Override
	protected CRM010Request generateRequestEntity(Configurate config, FaceVideo faceVideo, Map<String, String> map) throws WXSSException {
		if(null != faceVideo) {
			CRM010Request crm010Request = new CRM010Request();
			crm010Request.setApi_code("CRM010");
			crm010Request.setReq_serial_no(StringUtil.getSerialNo32());
			crm010Request.setReq_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));
			crm010Request.setSecurity_code(config.ESB_SEC_CODE);
			crm010Request.setSecurity_value(config.ESB_SEC_VALUE);
			crm010Request.setCust_id(String.valueOf(faceVideo.getCustId()));
			crm010Request.setCust_name(faceVideo.getCustName());
			crm010Request.setFirst_date(faceVideo.getFirstDate());
			crm010Request.setSecond_date(faceVideo.getSecondDate());
			
			try {
				crm010Request.setSign(Security.getSign(crm010Request, config.ESB_SIGN_KEY));
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage(), e);
				throw new WXSSException("SIGN_ERROR", "签名错误！");
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
				throw new WXSSException("SIGN_ERROR", "签名错误！");
			}
			return crm010Request;
		} else {
			throw new WXSSException("FAILED", "FaceVideo对象不存在！");
		}
	}

	
	@Override
	protected CRM010Response initResponseEntity() {
		return new CRM010Response();
	}
}
