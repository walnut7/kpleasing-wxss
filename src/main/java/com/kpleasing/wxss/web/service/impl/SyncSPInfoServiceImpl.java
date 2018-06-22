package com.kpleasing.wxss.web.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kpleasing.wxss.entity.SecurityKey;
import com.kpleasing.wxss.exception.InputParamException;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.protocol.request.SyncSPInfoRequest;
import com.kpleasing.wxss.protocol.response.SyncSPInfoResponse;
import com.kpleasing.wxss.util.DateUtil;
import com.kpleasing.wxss.util.Security;
import com.kpleasing.wxss.util.StringUtil;
import com.kpleasing.wxss.web.service.APIService;
import com.kpleasing.wxss.web.service.SyncSPInfoService;

@Service("SyncSPInfoService")
@Transactional
public class SyncSPInfoServiceImpl implements SyncSPInfoService {

	private static Logger logger = Logger.getLogger(SyncSPInfoServiceImpl.class);
	
	@Autowired
	private APIService apiService;

	/**
	 * 生成响应XML报文
	 * 
	 * @param syncSPInfoRequest
	 * @param syncSPInfoResp
	 * @return
	 */
	@Override
	public String generateSyncSPInfoResponseXML(SyncSPInfoRequest syncSPInfoRequest, SyncSPInfoResponse syncSPInfoResp,
			SecurityKey param) throws WXSSException {
		try {
			syncSPInfoResp.setReturn_code("SUCCESS");
			syncSPInfoResp.setReturn_desc("成功！");
			syncSPInfoResp.setReq_serial_no(syncSPInfoRequest.getReq_serial_no());
			syncSPInfoResp.setReq_date(syncSPInfoRequest.getReq_date());
			syncSPInfoResp.setRes_serial_no(StringUtil.getSerialNo32());
			syncSPInfoResp.setRes_date(DateUtil.getCurrentDate(DateUtil.yyyyMMddHHmmss));

			logger.info("salesInfo:"+syncSPInfoRequest.getSales().size());
			logger.info("开始校验内容.......");
			verification(syncSPInfoRequest, param);
			//保存信息
			apiService.saveSPAndSaleInfo(syncSPInfoRequest);
			
			syncSPInfoResp.setResult_code("SUCCESS");
			syncSPInfoResp.setResult_desc("操作成功！");

			syncSPInfoResp.setSign(Security.getSign(syncSPInfoResp, param.getSignKey()));

		} catch (InputParamException e) {
			syncSPInfoResp.setResult_code(e.getCode());
			syncSPInfoResp.setResult_desc(e.getDesc());
			try {
				Security.getSign(syncSPInfoResp, param.getSignKey());
			} catch (IllegalAccessException e1) {
				throw new WXSSException("FAILED", "签名失败");
			}
			logger.error(e.getMessage(), e);
			return generateFailedResponseXML(syncSPInfoResp);
			
		} catch (IllegalAccessException e) {
			throw new WXSSException("FAILED", "签名失败");
		}
		return generateSuccessfulResponseXML(syncSPInfoResp);
	}

	private void verification(SyncSPInfoRequest syncSPInfoRequest, SecurityKey param) throws InputParamException {
		// 访问权限校验
		if (!syncSPInfoRequest.getSecurity_code().equals(param.getSysName())
				|| !syncSPInfoRequest.getSecurity_value().equals(param.getSysCode())) {
			throw new InputParamException("权限受限！", syncSPInfoRequest.getReq_serial_no(),
					syncSPInfoRequest.getReq_date());
		}

		// 签名校验
		String signFromAPIResponse = syncSPInfoRequest.getSign();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			throw new InputParamException("API返回的数据签名数据不存在，有可能被第三方篡改!!!", syncSPInfoRequest.getReq_serial_no(),
					syncSPInfoRequest.getReq_date());
		}

		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		syncSPInfoRequest.setSign("");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		try {
			String signForAPIResponse = Security.getSign(syncSPInfoRequest, param.getSignKey());

			if (!signForAPIResponse.equals(signFromAPIResponse)) {
				// 签名验证不过，表示这个API返回的数据有可能已经被篡改了
				throw new InputParamException("API返回的数据签名验证不通过，有可能被第三方篡改!!!", syncSPInfoRequest.getReq_serial_no(),
						syncSPInfoRequest.getReq_date());
			}
		} catch (IllegalAccessException e) {
			throw new InputParamException("API签名出錯!!!", syncSPInfoRequest.getReq_serial_no(),
					syncSPInfoRequest.getReq_date());
		}

	}

	/**
	 * 生成成功响应报文
	 * 
	 * @param resp
	 * @return
	 */
	private String generateSuccessfulResponseXML(SyncSPInfoResponse resp) {
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

		logger.info("WSXX接口SYNC_SP_INFO响应成功报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

	/**
	 * 生成失败响应报文
	 * @param resp
	 * @return
	 */
	private String generateFailedResponseXML(SyncSPInfoResponse resp) {
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
		
		logger.info("WSXX接口SYNC_SP_INFO响应失败报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}
	
	@Override
	public String generateSyncSPInfoErrorXML(String code, String desc) {
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

		logger.info("WSXX接口SYNC_SP_INFO响应出错报文明文：" + msgResponse.toString());
		return msgResponse.toString();
	}

}
