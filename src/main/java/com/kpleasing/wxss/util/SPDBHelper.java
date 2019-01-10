package com.kpleasing.wxss.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import com.kpleasing.wxss.config.Configurate;
import com.kpleasing.wxss.mongo.collections.SpdbInterfaceLogCollection;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SPDBHelper {
	
	private static final Logger logger = Logger.getLogger(SPDBHelper.class);
	private static Map<String, String> mapBankCode = new HashMap<String, String>();
	
	static {
		mapBankCode.put("SPDB", "310290000013");
		mapBankCode.put("ICBC", "102100099996");
		mapBankCode.put("ABC", "103100000026");
		mapBankCode.put("BOC", "104100000004");
		mapBankCode.put("CCB", "105100000017");
		mapBankCode.put("BCOM", "301290000007");
		mapBankCode.put("CITIC", "302100011000");
		mapBankCode.put("CMB", "308584000013");
		mapBankCode.put("CEB", "303100000006");
		mapBankCode.put("HXB", "304100040000");
		mapBankCode.put("CMBC", "305100000013");
		mapBankCode.put("CGB", "306581000003");
		mapBankCode.put("CIB", "309391000011");
		mapBankCode.put("PAB", "307584007998");
		mapBankCode.put("恒丰银行", "315456000105");
		mapBankCode.put("浙商银行", "316331000018");
		mapBankCode.put("BHB", "318110000014");
		mapBankCode.put("中国邮储 ", "403100000004");
	}
	
	
	/**
	 * 浦发接口-随机密码获取
	 * @param config
	 * @param phone
	 * @param spdbLog 
	 * @return
	 */
	public static Map<String, Object> doSpdbGetAuthrCode(Configurate config, String phone, SpdbInterfaceLogCollection spdbLog) {
		StringBuilder content = new StringBuilder();
		content.append("{\"mobileNum\":\"").append(phone).append("\",")
		       .append("\"bizScene\":\"").append(config.SPDB_BIZ_SCENE1).append("\"}");
		logger.info("content is : " + content.toString());
		spdbLog.setRequest_message(content.toString());
		
		String resultJsonData = HttpHelper.doSpdbHttpPost(config.SPDB_GET_AUTHR_CODE_URL, content.toString(), config);
		spdbLog.setResponse_message(resultJsonData);
		return parseJSON2Map(resultJsonData);
	}
	
	
	
	/**
	 * 浦发接口-图文信息上传
	 * @param config
	 * @param spdbLog 
	 * @return
	 */
	public static Map<String, Object> doSpdbMediaUpload(Configurate config, String image1, String image2, SpdbInterfaceLogCollection spdbLog) {
		try {
			/*File file1 = new File(image1);
			if (file1.length()/1024>512) {
				Thumbnails.of(file1).scale(1f).outputQuality(0.25f).toFile(image1);
			}
			
			File file2 = new File(image2);
			if (file2.length()/1024>512) {
				Thumbnails.of(file2).scale(1f).outputQuality(0.25f).toFile(image2);
			}
			*/
			commpressPicCycle(image1, 512l);
			commpressPicCycle(image2, 512l);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		String resultJsonData = HttpHelper.doSpdbHttpPost(config.SPDB_MEDIA_UPLOAD_URL, null, config, image1, image2);
		spdbLog.setResponse_message(resultJsonData);
		return parseJSON2Map(resultJsonData);
	}
	
	
	/**
	 * 图片压缩至指定大小以下
	 * @param image
	 * @param desFileSize
	 * @throws IOException
	 */
	private static void commpressPicCycle(String image, long maxSize) throws IOException {
        File file = new File(image);
        long fileSize = file.length();
        
        if (fileSize <= maxSize * 1024) {
            return;
        }
        Thumbnails.of(file).scale(1f).outputQuality(0.25f).toFile(image);
        commpressPicCycle(image, maxSize);
    }
	
	

	/**
	 * 浦发接口-专用账户开立
	 * @param config
	 * @param data 
	 * @param spdbLog 
	 * @return
	 */
	public static Map<String, Object> doSpdbOpenAccount(Configurate config, Map<String, Object> data, SpdbInterfaceLogCollection spdbLog) {
		StringBuilder content = new StringBuilder();
		content.append("{\"idChnName\":\"").append((String)data.get("custname")).append("\",")
		       .append("\"identityId\":\"").append((String)data.get("identityId")).append("\",")
		       .append("\"instNum\":\"").append(config.SPDB_INST_NUM).append("\",")
		       .append("\"address\":\"").append((String)data.get("address")).append("\",")
		       .append("\"zipCode\":\"201203\",")
		       .append("\"mobileNum\":\"").append((String)data.get("mobileNum")).append("\",")
		       .append("\"bankNum\":\"").append(mapBankCode.get((String)data.get("bankNum"))).append("\",")
		       .append("\"cardNum\":\"").append((String)data.get("cardNum")).append("\",")
		       .append("\"memberId\":\"").append((String)data.get("memberId")).append("\",")
		       .append("\"occupationCode\":\"1\",")
		       .append("\"randomPassword\":\"").append((String)data.get("randomPassword")).append("\",")
		       .append("\"openInstNum\":\"\"}");
		logger.info("content is : " + content.toString());
		spdbLog.setRequest_message(content.toString());
		
		String resultJsonData = HttpHelper.doSpdbHttpPost(config.SPDB_OPEN_ACCOUNT_URL, content.toString(), config);
		spdbLog.setResponse_message(resultJsonData);
		return parseJSON2Map(resultJsonData);
	}
	
	
	
	/**
	 * 浦发接口-账户开立状态查询
	 * @param config
	 * @param spdbLog 
	 * @return
	 */
	public static Map<String, Object> doSpdbQueryAccount(Configurate config, String uuid, SpdbInterfaceLogCollection spdbLog) {
		StringBuilder content = new StringBuilder();
		content.append("{\"uuid\":\"").append(uuid).append("\"}");
		logger.info("content is : " + content.toString());
		spdbLog.setRequest_message(content.toString());
		
		String resultJsonData = HttpHelper.doSpdbHttpPost(config.SPDB_QUERY_ACCOUNT_URL, content.toString(), config);
		spdbLog.setResponse_message(resultJsonData);
		
		Map<String, Object> mapResultData = parseJSON2Map(resultJsonData);
		String resultCode = (String) mapResultData.get("statusCode");
		if("1000".equals(resultCode)) {
			try {
				Thread.sleep(3000);
				return doSpdbQueryAccount(config, uuid, spdbLog);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return mapResultData;
	}
	
	
	public static void main(String[] argc) {
		// String a = getReflectErrorInfo("F0000000001B", "S120020107/ReqDcmntIdntfy 图文信息上传失败，错误码：F0000000001B,没有识别到用户的身份证信息. dates:{errorCode2=F0000000001B, errorMessage2=没有识别到用户的身份证信息}");
		
		
		String a = "(开专户流水号：04971810192370407101377333) 专用账户开户 失败，错误码：卡校验失败EZ95接口 AuthnVrfyRslt: 1 chkRsltFlag: 3";
		
		System.out.println(a);
	}
	
	
	public static String getReflectErrorInfo(String msgCode, String msgDesc) {
		StringBuilder sbRtnMessage = new StringBuilder();
		
		if(msgDesc.indexOf("错误码：卡校验失败EZ95接口 AuthnVrfyRslt: 1 chkRsltFlag: 3")>1) {
			return "【开户失败！客户卡认证不通过。】";
		}
		
		if(msgDesc.indexOf("图文信息") != -1) {
			return "请重新上传身份证正反面！";
		}
		
		if(null == msgCode) {
			return msgDesc;
		} else if("0003".equals(msgCode)) {
			String regex = "[\\u4e00-\\u9fa5]{1,3}\\w+";
			Pattern p = Pattern.compile(regex);  
			Matcher m = p.matcher(msgDesc);  
			while(m.find()) {
				String errorCode = m.group();
				if("验失败DI11".equals(errorCode)) {
					regex = "\\s\\w+:\\s(\\w+)";
					p = Pattern.compile(regex);  
					m = p.matcher(msgDesc);
					
					while(m.find()) {
						String[] strs = m.group().split(":");
						if(strs.length>1) {
							String key = strs[0].trim();
							String value = strs[1].trim();
							
							if("bookAcctType".equals(key)) {
								if("AS00".equals(value)) {
									// sbRtnMessage.append("待开户客户");
									
									/*AS00:待开户
									AS01:已开户
									AS02:待销户
									AS03:已销户
									AS04：借记控制
									AS05：贷记控制
									AS06：冻结
									AS07: 已开户为Ⅰ类户 √
									AS08: 已开户为Ⅱ类户
									AS09: 已开户为Ⅲ类户
									AS10: 无此户*/
								}
								
								if("AS07".equals(value) || "AS08".equals(value) || "AS08".equals(value)) {
									// return "SUCCESS";
								}
							}
							
							if("idChkRslt1".equals(key)) {
								if("CV00".equals(value)) {	
									logger.info("身份校验通过");
								}
								if("CV01".equals(value)) {	
									sbRtnMessage.append("身份信息不正确，请检查身份信息后重试！");
								}
								if("CV02".equals(value)) {	
									logger.info("身份未校验");
								}
							}
							
							if("telChkRslt".equals(key)) {
								if("CV00".equals(value)) {
									logger.info("预留手机号校验通过");
								}
								if("CV01".equals(value)) {
									sbRtnMessage.append("预留手机号不正确，请确认开户行预留手机号后重试！");
								}
								if("CV02".equals(value)) {
									logger.info("预留手机号未校验");
								}
							}
						} else {
							logger.info(strs[0].trim());
						}
					}
				} else if("验失败EV93".equals(errorCode)) {
					regex = "\\s\\w+:\\s(\\w+)";
					p = Pattern.compile(regex);
					m = p.matcher(msgDesc);
					
					while(m.find()) {
						String[] strs = m.group().split(":");
						if(strs.length>1) {
							String key = strs[0].trim();
							String value = strs[1].trim();
							
							if("idCheckFlag".equals(key) && "1".equals(value)) {
								sbRtnMessage.append("身份信息校验未通过，请检查后重试！");
							}
							
							if("mblChkFlag".equals(key) && "1".equals(value)) {
								sbRtnMessage.append("银行预留手机号不正确，请检查后重试！");
							}
						} else {
							logger.info(strs[0].trim());
						}
					}
				} else if("未通过checkResult".equals(errorCode)){
					regex = "\\d+";
					p = Pattern.compile(regex);  
					m = p.matcher(msgDesc);
					
					while(m.find()) {
						String str = m.group().trim();
						if("02".equals(str)) {
							sbRtnMessage.append("身份证号码与姓名不匹配，请检查相关信息正确性后重试！");
						} else if("03".equals(str)) {
							sbRtnMessage.append("身份证号码不存在，请检查相关信息正确性后重试！");
						} else if("06".equals(str)) {
							sbRtnMessage.append("联网核查失败，人行系统异常，稍后重试！");
						} else {
							logger.info(str);
						}
					}
				} else {
					sbRtnMessage.append(msgDesc);
				}
			}
		}
		
		if("F0000000001B".equals(msgCode)) {
			return "身份证照片读取失败，请重新上传身份证！";
		} else {
			// return msgDesc;
		}
		return StringUtils.isNotBlank(sbRtnMessage)?sbRtnMessage.toString():msgDesc;
	}
	
	
	/**
	 * Json数组转换成List
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseJSON2List(String json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject j = it.next();
			list.add(parseJSON2Map(j.toString()));
		}
		return list;
	}

	
	/**
	 * Json数据转换成Map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJSON2Map(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject objJson = JSONObject.fromObject(json);
		for (Object k : objJson.keySet()) {
			Object v = objJson.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject objJson2 = it.next();
					logger.info(objJson2.toString());
					list.add(parseJSON2Map(objJson2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
}