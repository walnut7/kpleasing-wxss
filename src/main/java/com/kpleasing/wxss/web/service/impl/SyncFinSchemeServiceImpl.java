package com.kpleasing.wxss.web.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.entity.SecurityKey;
import com.kpleasing.wxss.exception.InputParamException;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.protocol.request.SyncFinSchemeRequest;
import com.kpleasing.wxss.protocol.response.SyncFinSchemeResponse;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.web.service.APIService;
import com.kpleasing.wxss.web.service.SyncFinSchemeService;

@Service("SyncFinSchemeService")
@Transactional
public class SyncFinSchemeServiceImpl implements SyncFinSchemeService {

	private static Logger logger = Logger.getLogger(SyncFinSchemeServiceImpl.class);
	
	@Autowired
	private APIService apiService;
	
	/**
	 * 生成响应XML报文
	 * @param syncFinSchemeRequest
	 * @param syncFinSchemeResp
	 * @param param
	 * @return
	 */
	@Override
	public String generateSyncFinSchemeResponseXML(SyncFinSchemeRequest syncFinSchemeRequest,
			SyncFinSchemeResponse syncFinSchemeResp, SecurityKey param) throws WXSSException {
		try {
			syncFinSchemeResp.setReturn_code("SUCCESS");
			syncFinSchemeResp.setReturn_desc("成功！");
			syncFinSchemeResp.setReq_serial_no(syncFinSchemeRequest.getReq_serial_no());
			syncFinSchemeResp.setReq_date(syncFinSchemeRequest.getReq_date());
			syncFinSchemeResp.setRes_serial_no(StringUtil.getSerialNo32());
			syncFinSchemeResp.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("开始校验内容.......");
			verification(syncFinSchemeRequest, param);
			//保存信息
			apiService.saveFinSchemeInfo(syncFinSchemeRequest);
			
			syncFinSchemeResp.setResult_code("SUCCESS");
			syncFinSchemeResp.setResult_desc("操作成功！");

			syncFinSchemeResp.setSign(Security.getSign(syncFinSchemeResp, param.getSignKey()));

		} catch (InputParamException e) {
			syncFinSchemeResp.setResult_code(e.getCode());
			syncFinSchemeResp.setResult_desc(e.getDesc());
			try {
				Security.getSign(syncFinSchemeResp, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new WXSSException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(syncFinSchemeResp);
			
		} catch (IllegalAccessException e) {
			throw new WXSSException("FAILED", "签名失败");
		}
		return generateSuccessfulResponseXML(syncFinSchemeResp);
	}

	private void verification(SyncFinSchemeRequest syncFinSchemeRequest, SecurityKey param) throws InputParamException {
		// 访问权限校验
		if (!syncFinSchemeRequest.getSecurity_code().equals(param.getSysName())
				|| !syncFinSchemeRequest.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", syncFinSchemeRequest.getReq_serial_no(),
					syncFinSchemeRequest.getReq_date());
		}

		// 签名校验
		String signFromAPIResponse = syncFinSchemeRequest.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", syncFinSchemeRequest.getReq_serial_no(),
					syncFinSchemeRequest.getReq_date());
		}

		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		syncFinSchemeRequest.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(syncFinSchemeRequest, param.getSignKey());

			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", syncFinSchemeRequest.getReq_serial_no(),
						syncFinSchemeRequest.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", syncFinSchemeRequest.getReq_serial_no(),
					syncFinSchemeRequest.getReq_date());
		}

	}
	
	/**
	 * 生成成功响应报文
	 * @param resp
	 * @return
	 */
	private String generateSuccessfulResponseXML(SyncFinSchemeResponse resp) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
		.append("<wsxx><head>")
		.append("<return_code>").append(resp.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(resp.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(resp.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(resp.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(resp.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(resp.getRes_date()).append("</res_date>")
		.append("<sign>").append(resp.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(resp.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(resp.getResult_desc()).append("</result_desc>")
		.append("</body></wsxx>");

		logger.info("WSXX接口SYNC_FIN_SCHEME响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	/**
	 * 生成失败响应报文
	 * @param resp
	 * @return
	 */
	private String generateFailedResponseXML(SyncFinSchemeResponse resp) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
		.append("<crm><head>")
		.append("<return_code>").append(resp.getReturn_code()).append("</return_code>")
		.append("<return_desc>").append(resp.getReturn_desc()).append("</return_desc>")
		.append("<req_serial_no>").append(resp.getReq_serial_no()).append("</req_serial_no>")
		.append("<req_date>").append(resp.getReq_date()).append("</req_date>")
		.append("<res_serial_no>").append(resp.getRes_serial_no()).append("</res_serial_no>")
		.append("<res_date>").append(resp.getRes_date()).append("</res_date>")
		.append("<sign>").append(resp.getSign()).append("</sign>")
		.append("</head><body>")
		.append("<result_code>").append(resp.getResult_code()).append("</result_code>")
		.append("<result_desc>").append(resp.getResult_desc()).append("</result_desc>")
		.append("</body></crm>");
		
		logger.info("WSXX接口SYNC_FIN_SCHEME响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
	
	@Override
	public String generateSyncFinSchemeErrorXML(String code, String desc) {
		StringBuilder msgResponse = new StringBuilder();
		msgResponse.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
		.append("<wsxx><head>")
		.append("<return_code>").append("FAILED").append("</return_code>")
		.append("<return_desc>").append("处理出错").append("</return_desc>")
		.append("<res_serial_no>").append(StringUtil.getSerialNo32()).append("</res_serial_no>")
		.append("<res_date>").append(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss)).append("</res_date>")
		.append("</head><body>")
		.append("<result_code>").append(code).append("</result_code>")
		.append("<result_desc>").append(desc).append("</result_desc>")
		.append("</body></wsxx>");

		logger.info("WSXX接口SYNC_FIN_SCHEME响应出错报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

}
