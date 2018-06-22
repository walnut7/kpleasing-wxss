package com.kpleasing.wxss.web.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.entity.SecurityKey;
import com.kpleasing.wxss.exception.InputParamException;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.protocol.request.SyncCarInfoRequest;
import com.kpleasing.wxss.protocol.response.SyncCarInfoResponse;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.web.service.APIService;
import com.kpleasing.wxss.web.service.SyncCarInfoService;

@Service("SyncCarInfoService")
@Transactional
public class SyncCarInfoServiceImpl implements SyncCarInfoService {

	private static Logger logger = Logger.getLogger(SyncCarInfoServiceImpl.class);
	
	@Autowired
	private APIService apiService;
	
	/**
	 * 生成响应XML报文
	 * 
	 * @param syncCarInfoRequest
	 * @param syncCarInfoResp
	 * @param param
	 * @return
	 */
	@Override
	public String generateSyncCarInfoResponseXML(SyncCarInfoRequest syncCarInfoRequest,
			SyncCarInfoResponse syncCarInfoResp, SecurityKey param) throws WXSSException {
		try {
			syncCarInfoResp.setReturn_code("SUCCESS");
			syncCarInfoResp.setReturn_desc("成功！");
			syncCarInfoResp.setReq_serial_no(syncCarInfoRequest.getReq_serial_no());
			syncCarInfoResp.setReq_date(syncCarInfoRequest.getReq_date());
			syncCarInfoResp.setRes_serial_no(StringUtil.getSerialNo32());
			syncCarInfoResp.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("开始校验内容.......");
			verification(syncCarInfoRequest, param);
			//保存信息
			apiService.saveCarAndParamInfo(syncCarInfoRequest);
			
			syncCarInfoResp.setResult_code("SUCCESS");
			syncCarInfoResp.setResult_desc("操作成功！");

			syncCarInfoResp.setSign(Security.getSign(syncCarInfoResp, param.getSignKey()));

		} catch (InputParamException e) {
			syncCarInfoResp.setResult_code(e.getCode());
			syncCarInfoResp.setResult_desc(e.getDesc());
			try {
				Security.getSign(syncCarInfoResp, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new WXSSException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(syncCarInfoResp);
			
		} catch (IllegalAccessException e) {
			throw new WXSSException("FAILED", "签名失败");
		}
		return generateSuccessfulResponseXML(syncCarInfoResp);
	}

	private void verification(SyncCarInfoRequest syncCarInfoRequest, SecurityKey param) throws InputParamException {
		// 访问权限校验
		if (!syncCarInfoRequest.getSecurity_code().equals(param.getSysName())
				|| !syncCarInfoRequest.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", syncCarInfoRequest.getReq_serial_no(),
					syncCarInfoRequest.getReq_date());
		}

		// 签名校验
		String signFromAPIResponse = syncCarInfoRequest.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", syncCarInfoRequest.getReq_serial_no(),
					syncCarInfoRequest.getReq_date());
		}

		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		syncCarInfoRequest.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(syncCarInfoRequest, param.getSignKey());

			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", syncCarInfoRequest.getReq_serial_no(),
						syncCarInfoRequest.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", syncCarInfoRequest.getReq_serial_no(),
					syncCarInfoRequest.getReq_date());
		}

	}
	
	/**
	 * 生成成功响应报文
	 * 
	 * @param resp
	 * @return
	 */
	private String generateSuccessfulResponseXML(SyncCarInfoResponse resp) {
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

		logger.info("WSXX接口SYNC_CAR_INFO响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	/**
	 * 生成失败响应报文
	 * @param resp
	 * @return
	 */
	private String generateFailedResponseXML(SyncCarInfoResponse resp) {
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
		
		logger.info("WSXX接口SYNC_CAR_INFO响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
	
	@Override
	public String generateSyncCarInfoErrorXML(String code, String desc) {
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

		logger.info("WSXX接口SYNC_CAR_INFO响应出错报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

}
