package com.kpleasing.wxss.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.entity.SecurityKey;
import com.kpleasing.wxss.exception.WXSSException;
import com.kpleasing.wxss.mongo.collections.SpdbInterfaceLogCollection;
import com.kpleasing.wxss.protocol.request.SyncCarInfoRequest;
import com.kpleasing.wxss.protocol.request.SyncCarParam;
import com.kpleasing.wxss.protocol.request.SyncFinSchemeRequest;
import com.kpleasing.wxss.protocol.request.SyncSPInfoRequest;
import com.kpleasing.wxss.protocol.request.SyncSaleInfo;
import com.kpleasing.wxss.protocol.response.SyncCarInfoResponse;
import com.kpleasing.wxss.protocol.response.SyncFinSchemeResponse;
import com.kpleasing.wxss.protocol.response.SyncSPInfoResponse;
import com.kpleasing.wxss.util.EncryptUtil;
import com.kpleasing.wxss.util.JsonUtil;
import com.kpleasing.wxss.util.XMLHelper;
import com.kpleasing.wxss.util.JsonUtil.JsonDateValueProcessor;
import com.kpleasing.wxss.web.service.ConfigService;
import com.kpleasing.wxss.web.service.SpdbLogsService;
import com.kpleasing.wxss.web.service.SyncCarInfoService;
import com.kpleasing.wxss.web.service.SyncFinSchemeService;
import com.kpleasing.wxss.web.service.SyncSPInfoService;

import net.sf.json.JsonConfig;



@Controller
@RequestMapping(value = "/api")
public class APIController {
	
	private static final Logger logger = Logger.getLogger(APIController.class);
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private SyncSPInfoService syncSPInfoService;
	
	@Autowired
	private SyncCarInfoService syncCarInfoService;
	
	@Autowired
	private SyncFinSchemeService syncFinSchemeService;
	
	@Autowired
	SpdbLogsService  spdbLogsService;
	
	/**
	 * 同步SP信息
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/syncSPInfo", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SyncSPInfo(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = configService.getConfig();
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("请求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			SyncSPInfoRequest syncSPInfoRequest = new SyncSPInfoRequest();
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(requestXml, "/wsxx/head|/wsxx/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(syncSPInfoRequest, map); 
			}
			
			logger.info("SP销售list......");
			List<Map<String,String>> mapList1 = XMLHelper.parseMultNodesXml(requestXml, "//sale");
			List<SyncSaleInfo> sales = new ArrayList<SyncSaleInfo>();
			for(Map<String,String> map1 : mapList1) {
				SyncSaleInfo sale = new SyncSaleInfo();
				BeanUtils.populate(sale, map1);
				sales.add(sale);
			}
			syncSPInfoRequest.setSales(sales);
			
			SecurityKey param = configService.getConfig().getSecurityKey(syncSPInfoRequest.getApi_code());
			
			logger.info("生成响应报文.......");
			SyncSPInfoResponse syncSPInfoResp = new SyncSPInfoResponse();
			String responseXML = syncSPInfoService.generateSyncSPInfoResponseXML(syncSPInfoRequest, syncSPInfoResp,param);  
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
			
		}catch (WXSSException e) {
			logger.error("处理失败："+ e.getMessage(), e);
			String responseXML = syncSPInfoService.generateSyncSPInfoErrorXML(e.getCode(), e.getDesc());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		}catch (Exception e) {
			logger.error("【SYNC_SP_INFO】处理失败："+e.getMessage(), e);
			String responseXML = syncSPInfoService.generateSyncSPInfoErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			logger.info("【SYNC_SP_INFO】响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	/**
	 * 同步车辆信息
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/syncCarInfo", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SyncCarInfo(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = configService.getConfig();
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("请求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			SyncCarInfoRequest syncCarInfoRequest = new SyncCarInfoRequest();
			//XMLHelper.getBeanFromXML(requestXml, syncCarInfoRequest);
			List<Map<String, String>> mapList = XMLHelper.parseMultNodesXml(requestXml, "/wsxx/head|/wsxx/body", false);
			for(Map<String, String> map : mapList) {
				BeanUtils.populate(syncCarInfoRequest, map); 
			}
			
			logger.info("车辆参数list......");
			List<Map<String,String>> mapList1 = XMLHelper.parseMultNodesXml(requestXml, "//car_param");
			List<SyncCarParam> car_params = new ArrayList<SyncCarParam>();
			for(Map<String,String> map1 : mapList1) {
				SyncCarParam car_param = new SyncCarParam();
				BeanUtils.populate(car_param, map1);
				car_params.add(car_param);
			}
			syncCarInfoRequest.setCar_params(car_params);
			
			SecurityKey param = configService.getConfig().getSecurityKey(syncCarInfoRequest.getApi_code());
			
			logger.info("生成响应报文.......");
			SyncCarInfoResponse syncCarInfoResp = new SyncCarInfoResponse();
			String responseXML = syncCarInfoService.generateSyncCarInfoResponseXML(syncCarInfoRequest, syncCarInfoResp,param);  
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
			
		}catch (WXSSException e) {
			logger.error("处理失败："+ e.getMessage(), e);
			String responseXML = syncCarInfoService.generateSyncCarInfoErrorXML(e.getCode(), e.getDesc());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		}catch (Exception e) {
			logger.error("【SYNC_CAR_INFO】处理失败："+e.getMessage(), e);
			String responseXML = syncCarInfoService.generateSyncCarInfoErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			logger.info("【SYNC_CAR_INFO】响应密文：" + msgResponse);
			return msgResponse;
		} 
	}
	
	/**
	 * 同步融资方案信息
	 * @param requestBody
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/syncFinanceScheme", method = RequestMethod.POST, consumes = "application/xml")
	public @ResponseBody String SyncFinanceScheme(@RequestBody String requestBody) throws Exception {
		logger.info("请求报文：" + requestBody);
		Configurate config = configService.getConfig();
		try {
			String requestXml = EncryptUtil.decrypt(config.DES_KEY, config.DES_IV, requestBody);
			logger.info("请求报文明文信息：" + requestXml);
			
			logger.info("开始解析内容.......");
			SyncFinSchemeRequest syncFinSchemeRequest = new SyncFinSchemeRequest();
			XMLHelper.getBeanFromXML(requestXml, syncFinSchemeRequest);
			
			SecurityKey param = configService.getConfig().getSecurityKey(syncFinSchemeRequest.getApi_code());
			
			logger.info("生成响应报文.......");
			SyncFinSchemeResponse syncFinSchemeResp = new SyncFinSchemeResponse();
			String responseXML = syncFinSchemeService.generateSyncFinSchemeResponseXML(syncFinSchemeRequest, syncFinSchemeResp, param);  
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
			
		}catch (WXSSException e) {
			logger.error("处理失败："+ e.getMessage(), e);
			String responseXML = syncFinSchemeService.generateSyncFinSchemeErrorXML(e.getCode(), e.getDesc());
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			logger.info("响应密文：" + msgResponse);
			return msgResponse;
		}catch (Exception e) {
			logger.error("【SYNC_FIN_SCHEME】处理失败："+e.getMessage(), e);
			String responseXML = syncFinSchemeService.generateSyncFinSchemeErrorXML("FAILED", "未知错误");
			String msgResponse = EncryptUtil.encrypt(config.DES_KEY, config.DES_IV, responseXML);
			logger.info("【SYNC_FIN_SCHEME】响应密文：" + msgResponse);
			return msgResponse;
		}
	}	
	
	
	/**
	 * 打开浦发日志查询页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "spdb_log", method = RequestMethod.GET)
	public ModelAndView redirectCertInfoA(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("manage/spdb_log");
	}
	
	
	/**
	 * 查询浦发操作日志信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "search_spdb_list", method = RequestMethod.POST)
	public @ResponseBody String searchSpdbLogsInfo(HttpServletRequest request, SpdbInterfaceLogCollection parameters) {
		logger.info("start to search spdb operation logs info .....");
		List<SpdbInterfaceLogCollection> spdbLogs = spdbLogsService.findSpdbOperateLog(parameters);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		return JsonUtil.JSON_List2String(spdbLogs, jsonConfig);
	}
	
}
